package com.tradeix.concord.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.TradeAssetContract
import com.tradeix.concord.contracts.TradeAssetContract.Companion.TRADE_ASSET_CONTRACT_ID
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.exceptions.FlowVerificationException
import com.tradeix.concord.helpers.FlowHelper
import com.tradeix.concord.helpers.VaultHelper
import com.tradeix.concord.messages.TradeAssetAmendmentRequestMessage
import com.tradeix.concord.models.TradeAsset
import com.tradeix.concord.states.TradeAssetState
import com.tradeix.concord.validators.TradeAssetAmendmentRequestMessageValidator
import net.corda.core.contracts.*
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import java.util.*

object TradeAssetAmendment {
    @InitiatingFlow
    @StartableByRPC
    class InitiatorFlow(private val message: TradeAssetAmendmentRequestMessage) : FlowLogic<SignedTransaction>() {

        companion object {

            private val EX_BUYER_CANNOT_AMEND_MSG =
                    "Trade asset can only be amended by the buyer when the status of the trade asset is Purchase Order."

            private val EX_SUPPLIER_CANNOT_AMEND_MSG =
                    "Trade asset can only be amended by the supplier when the status of the trade asset is Invoice."

            object GENERATING_TRANSACTION : ProgressTracker.Step("Generating transaction based on new trade asset.")
            object VERIFYING_TRANSACTION : ProgressTracker.Step("Verifying contracts constraints.")
            object SIGNING_TRANSACTION : ProgressTracker.Step("Signing transaction with our private key.")
            object GATHERING_SIGNATURES : ProgressTracker.Step("Gathering the counterparty's signature.") {
                override fun childProgressTracker() = CollectSignaturesFlow.tracker()
            }

            object FINALISING_TRANSACTION : ProgressTracker.Step("Obtaining notary signature and recording transaction.") {
                override fun childProgressTracker() = FinalityFlow.tracker()
            }

            fun tracker() = ProgressTracker(
                    GENERATING_TRANSACTION,
                    VERIFYING_TRANSACTION,
                    SIGNING_TRANSACTION,
                    GATHERING_SIGNATURES,
                    FINALISING_TRANSACTION
            )
        }

        override val progressTracker = tracker()

        @Suspendable
        override fun call(): SignedTransaction {

            val validator = TradeAssetAmendmentRequestMessageValidator(message)

            if (!validator.isValid) {
                throw FlowValidationException(validationErrors = validator.getValidationErrorMessages())
            }

            val notary = FlowHelper.getNotary(serviceHub)

            val inputStateAndRef = VaultHelper.getStateAndRefByLinearId(
                    serviceHub = serviceHub,
                    linearId = message.linearId,
                    contractStateType = TradeAssetState::class.java)

            val inputState = inputStateAndRef.state.data

            val amount = Amount.fromDecimal(
                    displayQuantity = message.value ?:
                            inputState.tradeAsset.amount.toDecimal(),
                    token = Currency.getInstance(message.currency ?:
                            inputState.tradeAsset.amount.token.currencyCode)
            )

            // val status = null // TODO : Can we amend status?

            val outputState = inputState.copy(
                    tradeAsset = TradeAsset(
                            amount = amount,
                            status = TradeAsset.TradeAssetStatus.INVOICE
                    )
            )

            // Stage 1 - Create an unsigned transaction
            progressTracker.currentStep = GENERATING_TRANSACTION

            val command = Command(
                    value = TradeAssetContract.Commands.Amend(),
                    signers = FlowHelper.getPublicKeysFromParticipants(outputState.participants)
            )

            val transactionBuilder = TransactionBuilder(notary)
                    .addInputState(inputStateAndRef)
                    .addOutputState(outputState, TRADE_ASSET_CONTRACT_ID)
                    .addCommand(command)


            // Stage 2 - Verify the transaction
            progressTracker.currentStep = VERIFYING_TRANSACTION
            verify(FlowHelper.getPeerByLegalNameOrMe(serviceHub, null), outputState)
            transactionBuilder.verify(serviceHub)

            // Stage 3 - Sign the transaction
            progressTracker.currentStep = SIGNING_TRANSACTION
            val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

            // Stage 4
            progressTracker.currentStep = GATHERING_SIGNATURES

            val requiredSignatureFlowSessions = listOf(
                    outputState.owner,
                    outputState.buyer,
                    outputState.supplier,
                    outputState.conductor)
                    .filter { !serviceHub.myInfo.legalIdentities.contains(it) }
                    .distinct()
                    .map { initiateFlow(it) }

            val fullySignedTransaction = subFlow(CollectSignaturesFlow(
                    partiallySignedTransaction,
                    requiredSignatureFlowSessions,
                    GATHERING_SIGNATURES.childProgressTracker()))

            // Stage 5 - Finalize transaction
            progressTracker.currentStep = FINALISING_TRANSACTION
            return subFlow(FinalityFlow(
                    fullySignedTransaction,
                    FINALISING_TRANSACTION.childProgressTracker()))
        }

        private fun verify(initiator: Party, state: TradeAssetState) {
            val errors = ArrayList<String>()

            if(initiator == state.buyer && state.tradeAsset.status != TradeAsset.TradeAssetStatus.PURCHASE_ORDER) {
                errors.add(EX_BUYER_CANNOT_AMEND_MSG)
            }

            if(initiator == state.supplier && state.tradeAsset.status != TradeAsset.TradeAssetStatus.INVOICE) {
                errors.add(EX_SUPPLIER_CANNOT_AMEND_MSG)
            }

            if(!errors.isEmpty()) {
                throw FlowVerificationException(verificationErrors = errors)
            }
        }
    }

    @InitiatedBy(TradeAssetAmendment.InitiatorFlow::class)
    class AcceptorFlow(val otherPartyFlow: FlowSession) : FlowLogic<SignedTransaction>() {
        @Suspendable
        override fun call(): SignedTransaction {
            val signTransactionFlow = object : SignTransactionFlow(otherPartyFlow) {
                override fun checkTransaction(stx: SignedTransaction) = requireThat {
                    val output = stx.tx.outputs.single().data
                    "This must be a trade asset transaction." using (output is TradeAssetState)
                }
            }

            return subFlow(signTransactionFlow)
        }
    }
}

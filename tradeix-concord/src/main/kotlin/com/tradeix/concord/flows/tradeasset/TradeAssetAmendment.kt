package com.tradeix.concord.flows.tradeasset

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.TradeAssetContract
import com.tradeix.concord.contracts.TradeAssetContract.Companion.TRADE_ASSET_CONTRACT_ID
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.exceptions.FlowVerificationException
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetAmendmentFlowModel
import com.tradeix.concord.helpers.FlowHelper
import com.tradeix.concord.helpers.VaultHelper
import com.tradeix.concord.states.TradeAssetState
import com.tradeix.concord.validators.tradeasset.TradeAssetAmendmentFlowModelValidator
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
    class InitiatorFlow(private val model: TradeAssetAmendmentFlowModel) : FlowLogic<SignedTransaction>() {

        companion object {

            private val EX_BUYER_CANNOT_AMEND_MSG =
                    "Trade asset of status PURCHASE_ORDER can only be amended by the buyer."

            private val EX_SUPPLIER_CANNOT_AMEND_MSG =
                    "Trade asset of status INVOICE can only be amended by the supplier."

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

            val validator = TradeAssetAmendmentFlowModelValidator(model)

            if (!validator.isValid) {
                throw FlowValidationException(validationErrors = validator.validationErrors)
            }

            val notary = FlowHelper.getNotary(serviceHub)

            val inputStateAndRef = VaultHelper.getStateAndRefByLinearId(
                    serviceHub = serviceHub,
                    linearId = model.getLinearId(),
                    contractStateType = TradeAssetState::class.java)

            val inputState = inputStateAndRef.state.data

            val amount = Amount.fromDecimal(
                    displayQuantity = model.value ?:
                            inputState.amount.toDecimal(),
                    token = Currency.getInstance(model.currency ?:
                            inputState.amount.token.currencyCode)
            )

            // val status = null // TODO : Can we amend status?

            val outputState = inputState.copy(
                    amount = amount,
                    status = inputState.status
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
                    .map { initiateFlow(serviceHub.identityService.requireWellKnownPartyFromAnonymous(it)) }

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

            if (initiator == state.buyer && state.status != TradeAssetState.TradeAssetStatus.PURCHASE_ORDER) {
                errors.add(EX_BUYER_CANNOT_AMEND_MSG)
            }

            if (initiator == state.supplier && state.status != TradeAssetState.TradeAssetStatus.INVOICE) {
                errors.add(EX_SUPPLIER_CANNOT_AMEND_MSG)
            }

            if (errors.isNotEmpty()) {
                throw FlowVerificationException(verificationErrors = errors)
            }
        }
    }

    @InitiatedBy(InitiatorFlow::class)
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

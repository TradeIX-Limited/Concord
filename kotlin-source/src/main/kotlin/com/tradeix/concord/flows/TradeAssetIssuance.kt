package com.tradeix.concord.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.TradeAssetContract
import com.tradeix.concord.contracts.TradeAssetContract.Companion.TRADE_ASSET_CONTRACT_ID
import com.tradeix.concord.exceptions.ValidationException
import com.tradeix.concord.helpers.FlowHelper
import com.tradeix.concord.helpers.VaultHelper
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.models.TradeAsset
import com.tradeix.concord.states.TradeAssetState
import net.corda.core.contracts.*
import net.corda.core.crypto.SecureHash.Companion.parse
import net.corda.core.flows.*
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import net.corda.core.utilities.ProgressTracker.Step

object TradeAssetIssuance {
    @InitiatingFlow
    @StartableByRPC
    class InitiatorFlow(private val message: TradeAssetIssuanceRequestMessage) : FlowLogic<SignedTransaction>() {

        companion object {
            object GENERATING_TRANSACTION : Step("Generating transaction based on new trade asset.")
            object VERIFYING_TRANSACTION : Step("Verifying contracts constraints.")
            object SIGNING_TRANSACTION : Step("Signing transaction with our private key.")
            object GATHERING_SIGNATURES : Step("Gathering the counterparty's signature.") {
                override fun childProgressTracker() = CollectSignaturesFlow.tracker()
            }

            object FINALISING_TRANSACTION : Step("Obtaining notary signature and recording transaction.") {
                override fun childProgressTracker() = FinalityFlow.tracker()
            }

            fun tracker() = ProgressTracker(
                    GENERATING_TRANSACTION,
                    VERIFYING_TRANSACTION,
                    SIGNING_TRANSACTION,
                    GATHERING_SIGNATURES,
                    FINALISING_TRANSACTION
            )
            val EX_INVALID_HASH_FOR_ATTACHMENT = "Invalid SecureHash for the Supporting Document"

        }

        override val progressTracker = tracker()

        @Suspendable
        override fun call(): SignedTransaction {

            if(!message.isValid) {
                throw ValidationException(validationErrors = message.getValidationErrors())
            }

            val notary = FlowHelper.getNotary(serviceHub)
            val buyer = FlowHelper.getPeerByLegalNameOrMe(serviceHub, message.buyer)
            val supplier = FlowHelper.getPeerByLegalNameOrThrow(serviceHub, message.supplier)
            val conductor = FlowHelper.getPeerByLegalNameOrThrow(serviceHub, message.conductor)
            if (message.attachmentHash !=null && !VaultHelper.isAttachmentInVault(serviceHub,message.attachmentHash)) {
                throw ValidationException(validationErrors = arrayListOf(EX_INVALID_HASH_FOR_ATTACHMENT))
            }

            // Stage 1 - Create unsigned transaction
            progressTracker.currentStep = GENERATING_TRANSACTION
            val outputState = TradeAssetState(
                    linearId = UniqueIdentifier(id = message.linearId),
                    tradeAsset = TradeAsset(
                            assetId = message.assetId!!,
                            status = TradeAsset.TradeAssetStatus.valueOf(message.status!!),
                            amount = message.amount),
                    owner = supplier,
                    buyer = buyer,
                    supplier = supplier,
                    conductor = conductor)

            val command = Command(
                    value = TradeAssetContract.Commands.Issue(),
                    signers = outputState.participants.map { it.owningKey })

            val transactionBuilder = TransactionBuilder(notary)
                    .addOutputState(outputState, TRADE_ASSET_CONTRACT_ID)
                    .addCommand(command)

            if (message.attachmentHash !=null)
            {
               transactionBuilder.addAttachment(parse(message.attachmentHash))
            }

            // Stage 2 - Verify transaction
            progressTracker.currentStep = VERIFYING_TRANSACTION
            transactionBuilder.verify(serviceHub)

            // Stage 3 - Sign the transaction
            progressTracker.currentStep = SIGNING_TRANSACTION
            val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

            // Stage 4 - Gather counterparty signatures
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
                    transaction = fullySignedTransaction,
                    progressTracker = FINALISING_TRANSACTION.childProgressTracker()))
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

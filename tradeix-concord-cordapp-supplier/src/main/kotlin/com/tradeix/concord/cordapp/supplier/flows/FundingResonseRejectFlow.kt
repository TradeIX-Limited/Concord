package com.tradeix.concord.cordapp.supplier.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesInitiatorFlow
import com.tradeix.concord.shared.cordapp.mapping.fundingresponse.FundingResponseRejectMapper
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.extensions.*
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRejectMessage
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.validators.FundingResponseRejectMessageValidator
import net.corda.core.contracts.Command
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import java.util.Arrays.asList

@StartableByRPC
@InitiatingFlow
class FundingResonseRejectFlow(
        private val message: FundingResponseRejectMessage
) : FlowLogic<SignedTransaction>() {

    override val progressTracker = getProgressTrackerWithObservationStep()

    @Suspendable
    override fun call(): SignedTransaction {

        val validator = FundingResponseRejectMessageValidator()
        val identityService = IdentityService(serviceHub)
        val mapper = FundingResponseRejectMapper(serviceHub)

        validator.validate(message)

        // Step 1 - Generating Unsigned Transaction
        progressTracker.currentStep = GeneratingTransactionStep
        val states: InputAndOutput<FundingResponseState> = mapper.map(message)

        val fundingResponseState= states.input

        val command = Command(
                FundingResponseContract.Reject(),
                identityService.getParticipants(asList(fundingResponseState.state.data)).toOwningKeys())

        //TODO: Ross and Grant review - The output state should be present
        val transactionBuilder = TransactionBuilder(identityService.getNotary())
                .addInputState(fundingResponseState)
                .addCommand(command)

        // Step 2 - Validate Unsigned Transaction
        progressTracker.currentStep = ValidatingTransactionStep
        transactionBuilder.verify(serviceHub)

        // Step 3 - Sign Unsigned Transaction
        progressTracker.currentStep = SigningTransactionStep
        val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

        // Step 4 - Gather Counterparty Signatures
        progressTracker.currentStep = GatheringSignaturesStep
        val fullySignedTransaction = subFlow(
                CollectSignaturesInitiatorFlow(
                        partiallySignedTransaction,
                        identityService.getWellKnownParticipantsExceptMe(asList(fundingResponseState.state.data)),
                        GatheringSignaturesStep.childProgressTracker()
                )
        )

        // Step 5 - Finalize Transaction
        progressTracker.currentStep = FinalizingTransactionStep
        return subFlow(FinalityFlow(fullySignedTransaction, FinalizingTransactionStep.childProgressTracker()))
    }
}
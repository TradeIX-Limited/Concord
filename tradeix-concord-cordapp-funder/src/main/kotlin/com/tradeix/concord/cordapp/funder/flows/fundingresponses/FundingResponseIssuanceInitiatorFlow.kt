package com.tradeix.concord.cordapp.funder.flows.fundingresponses

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.cordapp.funder.mappers.fundingresponses.FundingResponseIssuanceMapper
import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceRequestMessage
import com.tradeix.concord.cordapp.funder.validators.fundingresponses.FundingResponseIssuanceRequestMessageValidator
import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesInitiatorFlow
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract.Companion.FUNDING_RESPONSE_CONTRACT_ID
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.extensions.*
import com.tradeix.concord.shared.services.IdentityService
import net.corda.core.contracts.Command
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder

@StartableByRPC
@InitiatingFlow
class FundingResponseIssuanceInitiatorFlow(
        private val message: FundingResponseIssuanceRequestMessage
) : FlowLogic<SignedTransaction>() {

    override val progressTracker = getDefaultProgressTracker()

    @Suspendable
    override fun call(): SignedTransaction {

        val validator = FundingResponseIssuanceRequestMessageValidator()
        val identityService = IdentityService(serviceHub)
        val mapper = FundingResponseIssuanceMapper(serviceHub)

        validator.validate(message)

        // Step 1 - Generating Unsigned Transaction
        progressTracker.currentStep = GeneratingTransactionStep
        val fundingResponseState: FundingResponseState = mapper.map(message)

        val command = Command(
                FundingResponseContract.Issue(),
                fundingResponseState.participants.toOwningKeys()
        )

        val transactionBuilder = TransactionBuilder(identityService.getNotary())
                .addOutputState(fundingResponseState, FUNDING_RESPONSE_CONTRACT_ID)
                .addCommand(command)

        // Step 2 - Validating Unsigned Transaction
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
                        identityService.getWellKnownParticipantsExceptMe(fundingResponseState),
                        GatheringSignaturesStep.childProgressTracker()
                )
        )

        // Step 5 - Finalize Transaction
        progressTracker.currentStep = FinalizingTransactionStep
        return subFlow(FinalityFlow(fullySignedTransaction, FinalizingTransactionStep.childProgressTracker()))
    }
}
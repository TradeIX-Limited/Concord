package com.tradeix.concord.shared.extensions

import net.corda.core.flows.CollectSignaturesFlow
import net.corda.core.flows.FinalityFlow
import net.corda.core.utilities.ProgressTracker

object GeneratingTransactionStep : ProgressTracker.Step("Generating transaction based on a new state.")

object ValidatingTransactionStep : ProgressTracker.Step("Validating contract constraints.")

object SigningTransactionStep : ProgressTracker.Step("Signing transaction with our private key.")

object SendTransactionToObserversStep : ProgressTracker.Step("Sending transaction to observers.")

object GatheringSignaturesStep : ProgressTracker.Step("Gathering counterparties' signatures.") {
    override fun childProgressTracker() = CollectSignaturesFlow.tracker()
}

object FinalizingTransactionStep : ProgressTracker.Step("Obtaining notary signature and recording transaction.") {
    override fun childProgressTracker() = FinalityFlow.tracker()
}

fun getDefaultProgressTracker(): ProgressTracker {
    return ProgressTracker(
            GeneratingTransactionStep,
            ValidatingTransactionStep,
            SigningTransactionStep,
            GatheringSignaturesStep,
            FinalizingTransactionStep
    )
}
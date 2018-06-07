package com.tradeix.concord.shared.extensions

import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.CordaX500Name
import net.corda.core.transactions.SignedTransaction
import java.security.PublicKey

fun Iterable<AbstractParty>.toOwningKeys(): List<PublicKey> {
    return this.map { it.owningKey }
}

fun Iterable<AbstractParty>.getFlowSessionsForCounterparties(flow: FlowLogic<SignedTransaction>): Collection<FlowSession> {
    return this
            .filter { !flow.serviceHub.myInfo.legalIdentities.contains(it) }
            .distinct()
            .map { flow.initiateFlow(flow.serviceHub.identityService.requireWellKnownPartyFromAnonymous(it)) }
}
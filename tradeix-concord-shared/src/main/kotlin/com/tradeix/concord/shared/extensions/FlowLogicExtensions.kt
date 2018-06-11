package com.tradeix.concord.shared.extensions

import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.identity.Party

fun <T> FlowLogic<T>.flowSessionsFor(counterparties: Iterable<Party>): Collection<FlowSession> {
    return counterparties.map { initiateFlow(it) }
}
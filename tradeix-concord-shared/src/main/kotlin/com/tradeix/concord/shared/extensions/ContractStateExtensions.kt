package com.tradeix.concord.shared.extensions

import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party
import net.corda.core.node.ServiceHub
import java.security.PublicKey

fun Iterable<ContractState>.getAllParticipants(): Iterable<AbstractParty> {
    return this
            .flatMap { it.participants }
            .distinct()
}

fun Iterable<ContractState>.getAllParticipantsExceptMe(serviceHub: ServiceHub): Iterable<AbstractParty> {
    return this
            .flatMap { it.participants }
            .filter { !serviceHub.myInfo.legalIdentities.contains(it) }
            .distinct()
}

fun Iterable<ContractState>.getAllWellKnownParticipants(serviceHub: ServiceHub): Iterable<Party> {
    return this
            .getAllParticipants()
            .map { serviceHub.identityService.requireWellKnownPartyFromAnonymous(it) }
}

fun Iterable<ContractState>.getAllWellKnownParticipantsExceptMe(serviceHub: ServiceHub): Iterable<Party> {
    return this
            .getAllParticipantsExceptMe(serviceHub)
            .map { serviceHub.identityService.requireWellKnownPartyFromAnonymous(it) }
}

fun Iterable<ContractState>.getAllOwningKeys(): List<PublicKey> {
    return this
            .getAllParticipants()
            .map { it.owningKey }
            .distinct()
}
package com.tradeix.concord.shared.extensions

import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import java.security.PublicKey

fun Iterable<ContractState>.getAllParticipants(): Iterable<AbstractParty> {
    return this.flatMap { it.participants }.distinct()
}

fun Iterable<ContractState>.getAllOwningKeys(): List<PublicKey> {
    return this.getAllParticipants().map { it.owningKey }.distinct()
}
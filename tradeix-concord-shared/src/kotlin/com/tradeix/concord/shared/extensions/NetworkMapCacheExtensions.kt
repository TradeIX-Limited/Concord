package com.tradeix.concord.shared.extensions

import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.NetworkMapCache

private val EX_CANNOT_GET_PARTY_FROM_NULL_LEGAL_NAME = "Cannot resolve party from a null reference."
private val EX_FAILED_TO_GET_PARTY = "Failed to get party from X500 name"

fun NetworkMapCache.getNotaryParty(index: Int = 0): Party {
    return this.notaryIdentities[index]
}

fun NetworkMapCache.getPartyFromLegalNameOrThrow(cordaX500Name: CordaX500Name?): Party {
    return this.getPeerByLegalName(cordaX500Name ?: throw FlowException(EX_CANNOT_GET_PARTY_FROM_NULL_LEGAL_NAME))
            ?: throw FlowException("$EX_FAILED_TO_GET_PARTY '$cordaX500Name'.")
}

fun NetworkMapCache.getPartyFromLegalNameOrDefault(
        cordaX500Name: CordaX500Name?,
        defaultCordaX500Name: CordaX500Name) : Party {

    return this.getPeerByLegalName(cordaX500Name ?: defaultCordaX500Name)
            ?: throw FlowException("$EX_FAILED_TO_GET_PARTY '$cordaX500Name'.")
}

fun NetworkMapCache.getPartyFromLegalNameOrMe(serviceHub: ServiceHub, cordaX500Name: CordaX500Name?): Party {
    return this.getPeerByLegalName(cordaX500Name ?: serviceHub.myInfo.legalIdentities[0].name)
            ?: throw FlowException("$EX_FAILED_TO_GET_PARTY '$cordaX500Name'.")
}
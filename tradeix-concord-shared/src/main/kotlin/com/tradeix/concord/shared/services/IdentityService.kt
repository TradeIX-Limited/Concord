package com.tradeix.concord.shared.services

import net.corda.core.contracts.ContractState
import net.corda.core.flows.FlowException
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.node.ServiceHub

class IdentityService(private val serviceHub: ServiceHub) {

    companion object {
        private const val EX_CANNOT_GET_PARTY_FROM_NULL_LEGAL_NAME = "Cannot resolve party from a null reference."
        private const val EX_FAILED_TO_GET_PARTY = "Failed to get party from X500 name"
    }

    fun getNotary(index: Int = 0): Party {
        return serviceHub.networkMapCache.notaryIdentities[index]
    }

    fun getParticipants(states: Iterable<ContractState>): Iterable<AbstractParty> {
        return states
                .flatMap { it.participants }
                .distinct()
    }

    fun getParticipantsExceptMe(states: Iterable<ContractState>): Iterable<AbstractParty> {
        return getParticipants(states)
                .filter { !serviceHub.myInfo.legalIdentities.contains(it) }
                .distinct()
    }

    fun getWellKnownParticipants(states: Iterable<ContractState>): Iterable<Party> {
        return getParticipants(states)
                .map { serviceHub.identityService.requireWellKnownPartyFromAnonymous(it) }
    }

    fun getWellKnownParticipantsExceptMe(states: Iterable<ContractState>): Iterable<Party> {
        return getParticipantsExceptMe(states)
                .map { serviceHub.identityService.requireWellKnownPartyFromAnonymous(it) }
    }

    fun getPartyFromLegalNameOrMe(legalName: CordaX500Name?): Party {
        return serviceHub.networkMapCache.getPeerByLegalName(
                legalName ?: serviceHub.myInfo.legalIdentities[0].name
        ) ?: throw FlowException("$EX_FAILED_TO_GET_PARTY '$legalName'.")
    }

    fun getPartyFromLegalNameOrNull(legalName: CordaX500Name?): Party? {
        return legalName?.let { serviceHub.networkMapCache.getPeerByLegalName(legalName) }
    }

    fun getPartyFromLegalNameOrThrow(legalName: CordaX500Name?): Party {
        return serviceHub.networkMapCache.getPeerByLegalName(
                legalName ?: throw FlowException(EX_CANNOT_GET_PARTY_FROM_NULL_LEGAL_NAME)
        ) ?: throw FlowException("$EX_FAILED_TO_GET_PARTY '$legalName'.")
    }

}
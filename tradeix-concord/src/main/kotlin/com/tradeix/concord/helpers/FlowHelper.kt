package com.tradeix.concord.helpers

import net.corda.core.flows.FlowException
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.node.ServiceHub
import java.security.PublicKey

object FlowHelper {
    private val EX_NULL_CORDA_X500_NAME = "Cannot get a peer from a null legal name"
    private val EX_FAILED_TO_GET_IDENTITY = "Failed to get peer from legal name"
    val BNO_SELECTED_NOTARY_ORGANISATION_NAME ="BNO_SELECTED_NOTARY_ORGANISATION_NAME"

    fun getconfiguredNotaryOrganisationName() : String? {
       return System.getProperty(BNO_SELECTED_NOTARY_ORGANISATION_NAME)
    }

    fun getNotary(serviceHub: ServiceHub): Party {
      val configuredNotaryOrganisationName = getconfiguredNotaryOrganisationName()

        if(configuredNotaryOrganisationName ==null){
          return serviceHub.networkMapCache.notaryIdentities[0]
        }
        return getNotaryByOrganisationName(serviceHub, configuredNotaryOrganisationName)
    }
    fun getPeerByLegalNameOrNull(serviceHub: ServiceHub, cordaX500Name: CordaX500Name?): Party? {
        return if (cordaX500Name != null) {
            serviceHub.networkMapCache.getPeerByLegalName(cordaX500Name)
        } else null
    }

    fun getPeerByLegalNameOrThrow(serviceHub: ServiceHub, cordaX500Name: CordaX500Name?): Party = serviceHub
            .networkMapCache
            .getPeerByLegalName(cordaX500Name ?: throw FlowException(EX_NULL_CORDA_X500_NAME))
            ?: throw FlowException("$EX_FAILED_TO_GET_IDENTITY '$cordaX500Name'")

    fun getPeerByLegalNameOrMe(serviceHub: ServiceHub, cordaX500Name: CordaX500Name?): Party = serviceHub
            .networkMapCache
            .getPeerByLegalName(cordaX500Name ?: serviceHub.myInfo.legalIdentities[0].name)
            ?: throw FlowException("$EX_FAILED_TO_GET_IDENTITY '$cordaX500Name'")

    fun getPublicKeysFromParticipants(participants: List<AbstractParty>): List<PublicKey> = participants.map { it.owningKey }

    fun getNotaryByOrganisationName(serviceHub: ServiceHub, notaryOrganisationName: String) : Party {
      val notaryIdentities = serviceHub.networkMapCache.notaryIdentities
     return notaryIdentities.first {it.name.organisation == notaryOrganisationName}
    }
}

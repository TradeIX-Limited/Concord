package com.tradeix.concord.flows

import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.node.ServiceHub

object FlowHelper {

    private val EX_NULL_CORDA_X500_NAME = "Cannot get a peer from a null legal name"
    private val EX_FAILED_TO_GET_IDENTITY = "Failed to get peer from legal name"

    fun getNotary(serviceHub: ServiceHub): Party = serviceHub
            .networkMapCache
            .notaryIdentities[0]

    fun getPeerByLegalNameOrThrow(serviceHub: ServiceHub, cordaX500Name: CordaX500Name?): Party = serviceHub
            .networkMapCache
            .getPeerByLegalName(cordaX500Name ?: throw FlowException(EX_NULL_CORDA_X500_NAME))
            ?: throw FlowException("$EX_FAILED_TO_GET_IDENTITY '$cordaX500Name'")

    fun getPeerByLegalNameOrMe(serviceHub: ServiceHub, cordaX500Name: CordaX500Name?): Party = serviceHub
            .networkMapCache
            .getPeerByLegalName(cordaX500Name ?: serviceHub.myInfo.legalIdentities[0].name)
            ?: throw FlowException("$EX_FAILED_TO_GET_IDENTITY '$cordaX500Name'")
}
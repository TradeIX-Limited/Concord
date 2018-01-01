package com.tradeix.concord.extensions

import net.corda.core.contracts.ContractState
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.node.services.vault.PageSpecification

object CordaRPCOpsExtensions {
    inline fun <reified T : ContractState> CordaRPCOps.vaultCountBy(): Int = this
            .vaultQueryBy<T>(paging = PageSpecification(1, Int.MAX_VALUE))
            .statesMetadata
            .size
}
package com.tradeix.concord.helpers

import net.corda.core.contracts.ContractState
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.vault.QueryCriteria

object VaultHelper {
    fun <T : ContractState> getStateAndRefByLinearId(
            serviceHub: ServiceHub,
            linearId: UniqueIdentifier,
            contractStateType: Class<T>): StateAndRef<T> {
        return serviceHub
                .vaultService
                .queryBy(contractStateType, QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId)))
                .states
                .single()
    }
}

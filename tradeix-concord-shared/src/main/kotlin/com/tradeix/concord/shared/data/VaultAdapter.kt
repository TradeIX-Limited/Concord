package com.tradeix.concord.shared.data

import net.corda.core.contracts.ContractState
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.DataFeed
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.PageSpecification
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.node.services.vault.Sort

abstract class VaultAdapter<TState : ContractState>(protected val contractStateType: Class<TState>) {

    companion object {
        private val DEFAULT_CRITERIA = QueryCriteria.VaultQueryCriteria()
        private val DEFAULT_PAGING = PageSpecification()
        private val DEFAULT_SORTING = Sort(emptySet())

        inline fun <reified TState : ContractState> fromServiceHub(serviceHub: ServiceHub): VaultAdapter<TState> {
            return ServiceHubVaultAdapter(TState::class.java, serviceHub)
        }

        inline fun <reified TState : ContractState> fromCordaRPCOps(rpcOps: CordaRPCOps): VaultAdapter<TState> {
            return CordaRPCOpsVaultAdapter(TState::class.java, rpcOps)
        }
    }

    abstract fun vaultQueryBy(
            criteria: QueryCriteria = DEFAULT_CRITERIA,
            paging: PageSpecification = DEFAULT_PAGING,
            sorting: Sort = DEFAULT_SORTING
    ): Vault.Page<TState>

    abstract fun trackBy(
            criteria: QueryCriteria = DEFAULT_CRITERIA,
            paging: PageSpecification = DEFAULT_PAGING,
            sorting: Sort = DEFAULT_SORTING
    ): DataFeed<Vault.Page<TState>, Vault.Update<TState>>
}
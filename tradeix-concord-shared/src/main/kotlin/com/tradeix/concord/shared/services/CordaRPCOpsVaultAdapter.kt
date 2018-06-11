package com.tradeix.concord.shared.services

import net.corda.core.contracts.ContractState
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.DataFeed
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.PageSpecification
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.node.services.vault.Sort

@PublishedApi
internal class CordaRPCOpsVaultAdapter<TState : ContractState>(
        contractStateType: Class<TState>,
        private val rpcOps: CordaRPCOps
) : VaultAdapter<TState>(contractStateType) {

    override fun trackBy(
            criteria: QueryCriteria,
            paging: PageSpecification,
            sorting: Sort): DataFeed<Vault.Page<TState>, Vault.Update<TState>> {

        return rpcOps.vaultTrackBy(criteria, paging, sorting, contractStateType)
    }

    override fun vaultQueryBy(
            criteria: QueryCriteria,
            paging: PageSpecification,
            sorting: Sort): Vault.Page<TState> {

        return rpcOps.vaultQueryBy(criteria, paging, sorting, contractStateType)
    }
}
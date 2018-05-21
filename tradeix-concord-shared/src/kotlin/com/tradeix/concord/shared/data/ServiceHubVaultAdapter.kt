package com.tradeix.concord.shared.data

import net.corda.core.contracts.ContractState
import net.corda.core.messaging.DataFeed
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.PageSpecification
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.node.services.vault.Sort

@PublishedApi
internal class ServiceHubVaultAdapter<TState : ContractState>(
        contractStateType: Class<TState>,
        private val serviceHub: ServiceHub
) : VaultAdapter<TState>(contractStateType) {

    override fun trackBy(
            criteria: QueryCriteria,
            paging: PageSpecification,
            sorting: Sort): DataFeed<Vault.Page<TState>, Vault.Update<TState>> {

        return serviceHub
                .vaultService
                .trackBy(contractStateType, criteria, paging, sorting)
    }

    override fun vaultQueryBy(
            criteria: QueryCriteria,
            paging: PageSpecification,
            sorting: Sort): Vault.Page<TState> {

        return serviceHub
                .vaultService
                .queryBy(contractStateType, criteria, paging, sorting)
    }
}
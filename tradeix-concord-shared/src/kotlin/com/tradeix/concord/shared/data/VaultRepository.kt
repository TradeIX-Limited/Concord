package com.tradeix.concord.shared.data

import net.corda.core.contracts.ContractState
import net.corda.core.contracts.StateAndRef
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.PageSpecification
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class VaultRepository<TState : ContractState>(private val vaultAdapter: VaultAdapter<TState>) {

    companion object {
        private val MAX_PAGING = PageSpecification(1, Int.MAX_VALUE)

        inline fun <reified TState : ContractState> fromServiceHub(serviceHub: ServiceHub): VaultRepository<TState> {
            return VaultRepository(VaultAdapter.fromServiceHub(serviceHub))
        }

        inline fun <reified TState : ContractState> fromCordaRPCOps(rpcOps: CordaRPCOps): VaultRepository<TState> {
            return VaultRepository(VaultAdapter.fromCordaRPCOps(rpcOps))
        }
    }

    private val log: Logger = loggerFor<VaultRepository<TState>>()

    fun getPagedItems(
            pageNumber: Int = 1,
            pageSize: Int = 50,
            status: Vault.StateStatus = Vault.StateStatus.UNCONSUMED): Iterable<StateAndRef<TState>> {

        val criteria = QueryCriteria.VaultQueryCriteria(status = status)

        return vaultAdapter.vaultQueryBy(
                paging = PageSpecification(pageNumber, pageSize),
                criteria = criteria
        ).states
    }

    fun getCount(): Int {
        return vaultAdapter.vaultQueryBy(
                paging = MAX_PAGING
        ).statesMetadata.size
    }

    fun getLatestHash(): String {
        return vaultAdapter.vaultQueryBy(
                paging = MAX_PAGING
        ).states.last().ref.txhash.toString()
    }

    fun findByExternalId(
            externalId: String,
            pageNumber: Int = 1,
            pageSize: Int = 50,
            status: Vault.StateStatus = Vault.StateStatus.ALL): Iterable<StateAndRef<TState>> {

        val trimmedExternalId = externalId.trim()

        if (trimmedExternalId.isEmpty()) {
            throw IllegalArgumentException("externalId is required to query the vault.")
        }

        val criteria = QueryCriteria.LinearStateQueryCriteria(
                externalId = listOf(trimmedExternalId),
                status = status
        )

        return vaultAdapter.vaultQueryBy(
                criteria = criteria,
                paging = PageSpecification(pageNumber, pageSize)
        ).states
    }

    fun observe(func: (StateAndRef<TState>) -> Unit) {
        vaultAdapter.trackBy().updates.toBlocking().subscribe {
            it.produced.forEach {
                try {
                    func(it)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    log.error(ex.message, ex)
                }
            }
        }
    }
}
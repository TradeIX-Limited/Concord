package com.tradeix.concord.shared.services

import net.corda.core.contracts.StateAndRef
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.PageSpecification
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.schemas.QueryableState
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class VaultService<TState : QueryableState>(private val vaultAdapter: VaultAdapter<TState>) {

    companion object {
        private val MAX_PAGING = PageSpecification(1, Int.MAX_VALUE)

        inline fun <reified TState : QueryableState> fromServiceHub(serviceHub: ServiceHub): VaultService<TState> {
            return VaultService(VaultAdapter.fromServiceHub(serviceHub))
        }

        inline fun <reified TState : QueryableState> fromCordaRPCOps(rpcOps: CordaRPCOps): VaultService<TState> {
            return VaultService(VaultAdapter.fromCordaRPCOps(rpcOps))
        }
    }

    private val log: Logger = loggerFor<VaultService<TState>>()

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

    fun getCount(externalId: String): Int {
        return vaultAdapter.vaultQueryBy(
                paging = MAX_PAGING,
                criteria = QueryCriteria.LinearStateQueryCriteria(externalId = listOf(externalId))
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

        return findByCriteria(criteria, pageNumber, pageSize)
    }

    fun findByCriteria(
            queryCriteria: QueryCriteria,
            pageNumber: Int = 1,
            pageSize: Int = 50): Iterable<StateAndRef<TState>> {

        return vaultAdapter.vaultQueryBy(
                criteria = queryCriteria,
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
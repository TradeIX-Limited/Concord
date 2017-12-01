package com.tradeix.concord.helpers

import net.corda.core.contracts.ContractState
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.crypto.SecureHash
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.vault.QueryCriteria

object VaultHelper {
    fun <T : ContractState> getStateAndRefByLinearId(
            serviceHub: ServiceHub,
            linearId: UniqueIdentifier,
            contractStateType: Class<T>): StateAndRef<T> {

        val criteria = if (linearId.externalId != null) {
            QueryCriteria.LinearStateQueryCriteria(externalId = listOf(linearId.externalId!!))
        } else {
            QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId))
        }

        return serviceHub
                .vaultService
                .queryBy(contractStateType, criteria)
                .states
                .single()
    }

    fun isAttachmentInVault(serviceHub: ServiceHub, supportingDocumentHash: String): Boolean =
            try {
                (serviceHub.attachments.openAttachment(SecureHash.parse(supportingDocumentHash)) != null)
            } catch (e: IllegalAccessException) {
                false
            }
}

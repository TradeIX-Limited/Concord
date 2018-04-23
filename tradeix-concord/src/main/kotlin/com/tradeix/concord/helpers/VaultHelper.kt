package com.tradeix.concord.helpers

import com.tradeix.concord.interfaces.IQueueProducer
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderIssuanceRequestMessage
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.core.contracts.ContractState
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.crypto.SecureHash
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class VaultHelper {
    val log: Logger = loggerFor<VaultHelper>()

    fun watchPOState(rpcOps: CordaRPCOps, publisher: IQueueProducer<RabbitRequestMessage>) {
        val dataFeed = rpcOps.vaultTrack(PurchaseOrderState::class.java)
        val updates = dataFeed.updates

        updates.toBlocking().subscribe { update ->
            update.produced.forEach {
                try {
                    println("watchPOState = " + PurchaseOrderIssuanceRequestMessage.fromState(it.state.data))
                    publisher.publish(PurchaseOrderIssuanceRequestMessage.fromState(it.state.data))
                    println("Published PO")
                } catch (e: Exception) {
                    //todo remove the printstack when going to prod
                    e.printStackTrace()
                    log.error(e.message, e)
                }
            }
        }
    }

    companion object VaultHelper {

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
}

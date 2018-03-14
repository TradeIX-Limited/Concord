package com.tradeix.concord.apis

import com.tradeix.concord.helpers.VaultHelper
import com.tradeix.concord.interfaces.IQueueProducer
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import com.tradeix.concord.services.messaging.TixMessageSubscriptionStartup
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class TiXNotifier(val services: CordaRPCOps) {

    init {
        val log: Logger = loggerFor<TiXNotifier>()
        val legalEntity = services.nodeInfo().legalIdentities.first()

        if (legalEntity.name.organisation == "TradeIX") {
            val lock = java.lang.Object()
            synchronized(lock) {
                if (TixMessageSubscriptionStartup.currentPublishers.size != 2) {
                   lock.wait(3000)
                }
            }
            log.info("TiXNotifier for ${legalEntity.name.organisation}")
            if (TixMessageSubscriptionStartup.currentPublishers.size==2) {
                val tradeAssetpublisher = TixMessageSubscriptionStartup.currentPublishers["cordatix_tradeasset_notification"]
                val poAssetpublisher = TixMessageSubscriptionStartup.currentPublishers["cordatix_po_notification"]
                startTradeAsset(tradeAssetpublisher)
                startPO(poAssetpublisher)
            }else {
                log.error("Unable to connect to TiX Publishers because currentPublishers.size = ${TixMessageSubscriptionStartup.currentPublishers.size}")
            }
        }
    }

    fun runnable(f: () -> Unit): Runnable = object : Runnable {
        override fun run() {
            f()
        }
    }

    private fun startTradeAsset(tradeAssetpublisher: IQueueProducer<RabbitRequestMessage>?) {
        val tradeAssetRunnable = runnable {
            VaultHelper().watchTradeAssetState(services, tradeAssetpublisher!!)
        }
        Thread(tradeAssetRunnable).start()
    }

    private fun startPO(poPublisher: IQueueProducer<RabbitRequestMessage>?) {
        val poRunnable = runnable {
            VaultHelper().watchPOState(services, poPublisher!!)
        }
        Thread(poRunnable).start()
    }

}
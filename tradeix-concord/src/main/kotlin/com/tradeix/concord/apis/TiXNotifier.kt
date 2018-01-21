package com.tradeix.concord.apis

import com.tradeix.concord.helpers.VaultHelper
import com.tradeix.concord.services.messaging.TixMessageSubscriptionStartup
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

//TODO: This should be an Object
class TiXNotifier(val services: CordaRPCOps) {
    init {
        val log: Logger = loggerFor<TiXNotifier>()
        val legalEntity = services.nodeInfo().legalIdentities.single()

        if (legalEntity.name.organisation == "TradeIX") {
            log.debug("TiXNotifier for ${legalEntity.name.organisation}")
            val tradeAssetpublisher = TixMessageSubscriptionStartup.currentPublishers["cordatix_tradeasset_notification"]
            fun runnable(f: () -> Unit): Runnable = object : Runnable {override fun run() {f()}}
            val runnableList = ArrayList<Runnable>()

            val tradeAssetRunnable = runnable {
                VaultHelper().watchTradeAssetState(services, tradeAssetpublisher!!)
            }

            runnableList.add(tradeAssetRunnable)
            Thread(tradeAssetRunnable).start()
        }

    }

}
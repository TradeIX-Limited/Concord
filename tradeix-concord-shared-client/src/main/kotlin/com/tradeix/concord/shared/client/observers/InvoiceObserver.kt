package com.tradeix.concord.shared.client.observers

import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.client.components.OAuthAccessTokenProvider
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.components.TIXConfiguration
import com.tradeix.concord.shared.client.http.TixHttpClient
import com.tradeix.concord.shared.cordapp.mapping.invoices.InvoiceResponseMapper
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.messages.invoices.InvoiceBatchUploadResponseMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceResponseMessage
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import java.util.*
import kotlin.concurrent.timer

class InvoiceObserver(
        private val tixConfiguration: TIXConfiguration,
        rpc: RPCConnectionProvider,
        tokenProvider: OAuthAccessTokenProvider) {

    companion object {
        private val logger: Logger = loggerFor<InvoiceObserver>()
    }

    private val repository = VaultService.fromCordaRPCOps<InvoiceState>(rpc.proxy)
    private val client = TixHttpClient(tixConfiguration, tokenProvider)
    private val serializer = GsonBuilder().getConfiguredSerializer()
    private val mapper = InvoiceResponseMapper()
    private val invoices = mutableListOf<InvoiceResponseMessage>()

    private var tmr: Timer? = null

    fun observe() {
        repository.observe {
            tmr?.cancel()

            invoices.add(mapper.map(it.state.data))

            tmr = timer(period = 20000, action = {
                try {
                    val json = serializer.toJson(InvoiceBatchUploadResponseMessage(invoices))

                    logger.info(json)
                    client.post("v1/import/invoices", json, Unit::class.java)
                } catch (ex: Exception) {
                    logger.error(ex.message)
                } finally {
                    invoices.clear() // TODO : What happens if there was an exception thrown?
                    tmr?.cancel()
                }
            })
        }
    }
}
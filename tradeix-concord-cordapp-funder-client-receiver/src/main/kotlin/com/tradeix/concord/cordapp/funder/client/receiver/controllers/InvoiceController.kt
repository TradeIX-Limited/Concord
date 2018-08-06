package com.tradeix.concord.cordapp.funder.client.receiver.controllers

import com.tradeix.concord.cordapp.funder.flows.invoices.InvoiceTransferInitiatorFlow
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceTransactionResponseMessage
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceTransferTransactionRequestMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.mappers.InvoiceResponseMapper
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.services.VaultService
import com.tradeix.concord.shared.validation.ValidationException
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.node.services.Vault
import net.corda.core.utilities.getOrThrow
import net.corda.core.utilities.loggerFor
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Callable

@RestController
@RequestMapping(path = arrayOf("/invoices"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class InvoiceController(private val rpc: RPCConnectionProvider) {
    private val vaultService = VaultService.fromCordaRPCOps<InvoiceState>(rpc.proxy)
    private val invoiceResponseMapper = InvoiceResponseMapper()

    companion object {
        private val logger: Logger = loggerFor<InvoiceController>()
    }

    @GetMapping()
    fun getInvoiceStates(
            @RequestParam(name = "externalId", required = false) externalId: String?,
            @RequestParam(name = "status", required = false, defaultValue = "unconsumed") status: String,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") pageNumber: Int,
            @RequestParam(name = "pageSize", required = false, defaultValue = "50") pageSize: Int
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val stateStatus = Vault.StateStatus.valueOf(status.toUpperCase())

                if (externalId.isNullOrBlank()) {
                    val invoices = vaultService
                            .getPagedItems(pageNumber, pageSize, stateStatus)
                            .map { invoiceResponseMapper.map(it.state.data) }

                    ResponseBuilder.ok(mapOf("invoices" to invoices))
                } else {
                    val invoices = vaultService
                            .findByExternalId(externalId!!, pageNumber, pageSize, stateStatus)
                            .map { invoiceResponseMapper.map(it.state.data) }

                    ResponseBuilder.ok(mapOf("invoices" to invoices))
                }
            } catch (ex: Exception) {
                when (ex) {
                    is IllegalArgumentException -> ResponseBuilder.badRequest(ex.message)
                    else -> ResponseBuilder.internalServerError(ex.message)
                }
            }
        }
    }

    @GetMapping(path = arrayOf("{externalId}"))
    fun getUnconsumedInvoiceStateByExternalId(
            @PathVariable externalId: String
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val invoice = vaultService
                        .findByExternalId(externalId, status = Vault.StateStatus.UNCONSUMED)
                        .map { invoiceResponseMapper.map(it.state.data) }
                        .singleOrNull()

                ResponseBuilder.ok(mapOf("invoice" to invoice))
            } catch (ex: Exception) {
                when (ex) {
                    is IllegalArgumentException -> ResponseBuilder.badRequest(ex.message)
                    else -> ResponseBuilder.internalServerError(ex.message)
                }
            }
        }
    }

    @GetMapping(path = arrayOf("/count"))
    fun getUniqueInvoiceCount(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(mapOf("count" to vaultService.getCount()))
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/hash"))
    fun getMostRecentInvoiceHash(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(mapOf("hash" to vaultService.getLatestHash()))
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @PutMapping(path = arrayOf("/transfer"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun transferInvoice(
            @RequestBody message: InvoiceTransferTransactionRequestMessage
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val future = rpc.proxy.startTrackedFlow(::InvoiceTransferInitiatorFlow, message)
                future.progress.subscribe { println(it) }
                val result = future.returnValue.getOrThrow()
                val response = InvoiceTransactionResponseMessage(
                        externalIds = result.tx.outputsOfType<InvoiceState>().map { it.linearId.externalId!! },
                        transactionId = result.tx.id.toString()
                )

                Configurator.setLevel(logger.name, Level.DEBUG)
                logger.debug("*** SUCCESSFULLY TRANSFER INVOICE>> Transaction Id: " + response.transactionId)

                ResponseBuilder.ok(response)
            } catch (ex: Exception) {
                when (ex) {
                    is ValidationException -> ResponseBuilder.validationFailed(ex.validationMessages)
                    else -> ResponseBuilder.internalServerError(ex.message)
                }
            }
        }
    }
}
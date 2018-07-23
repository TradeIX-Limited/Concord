package com.tradeix.concord.cordapp.supplier.client.receiver.controllers

import com.tradeix.concord.cordapp.supplier.flows.invoices.InvoiceAmendmentInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.invoices.InvoiceCancellationInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.invoices.InvoiceIssuanceInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.invoices.InvoiceOwnershipChangeInitiatorFlow
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceCancellationTransactionRequestMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransactionRequestMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransactionResponseMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransferTransactionRequestMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.mappers.InvoiceResponseMapper
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.services.VaultService
import com.tradeix.concord.shared.validation.ValidationException
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.node.services.Vault
import net.corda.core.utilities.getOrThrow
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Callable

@RestController
@RequestMapping(path = arrayOf("/invoices"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class InvoiceController(private val rpc: RPCConnectionProvider) {

    private val vaultService = VaultService.fromCordaRPCOps<InvoiceState>(rpc.proxy)
    private val invoiceResponseMapper = InvoiceResponseMapper()

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

    @PostMapping(path = arrayOf("/issue"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun issueInvoice(
            @RequestBody message: InvoiceTransactionRequestMessage
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val future = rpc.proxy.startTrackedFlow(::InvoiceIssuanceInitiatorFlow, message)
                future.progress.subscribe { println(it) }
                val result = future.returnValue.getOrThrow()
                val response = InvoiceTransactionResponseMessage(
                        externalIds = result.tx.outputsOfType<InvoiceState>().map { it.linearId.externalId!! },
                        transactionId = result.tx.id.toString()
                )

                ResponseBuilder.ok(response)
            } catch (ex: Exception) {
                when (ex) {
                    is ValidationException -> ResponseBuilder.validationFailed(ex.validationMessages)
                    else -> ResponseBuilder.internalServerError(ex.message)
                }
            }
        }
    }

    @PutMapping(path = arrayOf("/amend"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun amendInvoice(
            @RequestBody message: InvoiceTransactionRequestMessage
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val future = rpc.proxy.startTrackedFlow(::InvoiceAmendmentInitiatorFlow, message)
                future.progress.subscribe { println(it) }
                val result = future.returnValue.getOrThrow()
                val response = InvoiceTransactionResponseMessage(
                        externalIds = result.tx.outputsOfType<InvoiceState>().map { it.linearId.externalId!! },
                        transactionId = result.tx.id.toString()
                )

                ResponseBuilder.ok(response)
            } catch (ex: Exception) {
                when (ex) {
                    is ValidationException -> ResponseBuilder.validationFailed(ex.validationMessages)
                    else -> ResponseBuilder.internalServerError(ex.message)
                }
            }
        }
    }

    @PutMapping(path = arrayOf("/changeowner"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun changeInvoiceOwner(
            @RequestBody message: InvoiceTransferTransactionRequestMessage
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val future = rpc.proxy.startTrackedFlow(::InvoiceOwnershipChangeInitiatorFlow, message)
                future.progress.subscribe { println(it) }
                val result = future.returnValue.getOrThrow()
                val response = InvoiceTransactionResponseMessage(
                        externalIds = result.tx.outputsOfType<InvoiceState>().map { it.linearId.externalId!! },
                        transactionId = result.tx.id.toString()
                )

                ResponseBuilder.ok(response)
            } catch (ex: Exception) {
                when (ex) {
                    is ValidationException -> ResponseBuilder.validationFailed(ex.validationMessages)
                    else -> ResponseBuilder.internalServerError(ex.message)
                }
            }
        }
    }

    @DeleteMapping(path = arrayOf("/cancel"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun cancelInvoice(
            @RequestBody message: InvoiceCancellationTransactionRequestMessage
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val future = rpc.proxy.startTrackedFlow(::InvoiceCancellationInitiatorFlow, message)
                future.progress.subscribe { println(it) }
                val result = future.returnValue.getOrThrow()
                val response = InvoiceTransactionResponseMessage(
                        externalIds = result.tx.outputsOfType<InvoiceState>().map { it.linearId.externalId!! },
                        transactionId = result.tx.id.toString()
                )

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
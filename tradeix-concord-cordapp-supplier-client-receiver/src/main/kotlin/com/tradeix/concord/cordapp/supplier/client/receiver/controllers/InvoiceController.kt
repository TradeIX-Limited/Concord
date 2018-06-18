package com.tradeix.concord.cordapp.supplier.client.receiver.controllers

import com.tradeix.concord.cordapp.supplier.flows.InvoiceAmendmentInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.InvoiceCancellationInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.InvoiceIssuanceInitiatorFlow
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.cordapp.mapping.invoices.InvoiceResponseMapper
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.messages.CancellationTransactionRequestMessage
import com.tradeix.concord.shared.messages.InvoiceTransactionRequestMessage
import com.tradeix.concord.shared.messages.TransactionResponseMessage
import com.tradeix.concord.shared.services.VaultService
import com.tradeix.concord.shared.validation.ValidationException
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.node.services.Vault
import net.corda.core.utilities.getOrThrow
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    ): ResponseEntity<*> {

        return try {
            val stateStatus = Vault.StateStatus.valueOf(status.toUpperCase())

            if (externalId.isNullOrBlank()) {
                val invoices = vaultService
                        .getPagedItems(pageNumber, pageSize, stateStatus)
                        .map { invoiceResponseMapper.map(it.state.data) }

                ResponseBuilder.ok(invoices)
            } else {
                val invoices = vaultService
                        .findByExternalId(externalId!!, pageNumber, pageSize, stateStatus)
                        .map { invoiceResponseMapper.map(it.state.data) }

                ResponseBuilder.ok(invoices)
            }
        } catch (ex: Exception) {
            when (ex) {
                is IllegalArgumentException -> ResponseBuilder.badRequest(ex.message)
                else -> ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("{externalId}"))
    fun getUnconsumedInvoiceStateByExternalId(@PathVariable externalId: String): ResponseEntity<*> {
        return try {
            val invoice = vaultService
                    .findByExternalId(externalId, status = Vault.StateStatus.UNCONSUMED)
                    .map { invoiceResponseMapper.map(it.state.data) }
                    .single()

            ResponseBuilder.ok(invoice)
        } catch (ex: Exception) {
            when (ex) {
                is IllegalArgumentException -> ResponseBuilder.badRequest(ex.message)
                else -> ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/count"))
    fun getUniqueInvoiceCount(): ResponseEntity<*> {
        return try {
            ResponseBuilder.ok(mapOf("count" to vaultService.getCount()))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }

    @GetMapping(path = arrayOf("/hash"))
    fun getMostRecentInvoiceHash(): ResponseEntity<*> {
        return try {
            ResponseBuilder.ok(mapOf("hash" to vaultService.getLatestHash()))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }

    @PostMapping(path = arrayOf("/issue"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun issueInvoice(@RequestBody message: InvoiceTransactionRequestMessage): ResponseEntity<*> {
        return try {
            val future = rpc.proxy.startTrackedFlow(::InvoiceIssuanceInitiatorFlow, message)
            future.progress.subscribe { println(it) }
            val result = future.returnValue.getOrThrow()
            val response = TransactionResponseMessage(
                    assetIds = result.tx.outputsOfType<InvoiceState>().map { it.linearId },
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

    @PutMapping(path = arrayOf("/amend"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun amendInvoice(@RequestBody message: InvoiceTransactionRequestMessage): ResponseEntity<*> {
        return try {
            val future = rpc.proxy.startTrackedFlow(::InvoiceAmendmentInitiatorFlow, message)
            future.progress.subscribe { println(it) }
            val result = future.returnValue.getOrThrow()
            val response = TransactionResponseMessage(
                    assetIds = result.tx.outputsOfType<InvoiceState>().map { it.linearId },
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

    @DeleteMapping(path = arrayOf("/cancel"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun cancelInvoice(@RequestBody message: CancellationTransactionRequestMessage): ResponseEntity<*> {
        return try {
            val future = rpc.proxy.startTrackedFlow(::InvoiceCancellationInitiatorFlow, message)
            future.progress.subscribe { println(it) }
            val result = future.returnValue.getOrThrow()
            val response = TransactionResponseMessage(
                    assetIds = emptyList(),
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
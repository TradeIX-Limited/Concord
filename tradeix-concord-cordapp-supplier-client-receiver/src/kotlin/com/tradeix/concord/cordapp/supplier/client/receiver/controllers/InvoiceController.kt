package com.tradeix.concord.cordapp.supplier.client.receiver.controllers

import com.tradeix.concord.cordapp.supplier.flows.InvoiceIssuanceInitiatorFlow
import com.tradeix.concord.shared.client.rpc.RPCProxy
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.data.VaultRepository
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.messages.TransactionResponseMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.shared.validation.ValidationException
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.node.services.Vault
import net.corda.core.utilities.getOrThrow
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = arrayOf("/invoices"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class InvoiceController {

    private val proxy = RPCProxy.proxy
    private val repository = VaultRepository.fromCordaRPCOps<InvoiceState>(proxy)

    @GetMapping()
    fun getInvoiceStates(
            @RequestParam(
                    name = "externalId",
                    required = false
            ) externalId: String?,

            @RequestParam(
                    name = "status",
                    required = false,
                    defaultValue = "unconsumed"
            ) status: String,

            @RequestParam(
                    name = "pageNumber",
                    required = false,
                    defaultValue = "1"
            ) pageNumber: Int,

            @RequestParam(
                    name = "pageSize",
                    required = false,
                    defaultValue = "50"
            ) pageSize: Int): ResponseEntity<*> {

        return try {
            val stateStatus = Vault.StateStatus.valueOf(status.toUpperCase())

            if (externalId.isNullOrBlank()) {
                ResponseBuilder.ok(repository.getPagedItems(pageNumber, pageSize, stateStatus))
            } else {
                ResponseBuilder.ok(repository.findByExternalId(externalId!!, pageNumber, pageSize, stateStatus))
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
            ResponseBuilder.ok(repository.findByExternalId(externalId, status = Vault.StateStatus.UNCONSUMED).single())
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
            ResponseBuilder.ok(mapOf("count" to repository.getCount()))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }

    @GetMapping(path = arrayOf("/hash"))
    fun getMostRecentInvoiceHash(): ResponseEntity<*> {
        return try {
            ResponseBuilder.ok(mapOf("hash" to repository.getLatestHash()))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }

    @PostMapping(path = arrayOf("/issue"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun issueInvoice(message: InvoiceRequestMessage): ResponseEntity<*> {
        return try {
            val flowHandle = proxy.startTrackedFlow(::InvoiceIssuanceInitiatorFlow, message)
            val result = flowHandle.returnValue.getOrThrow()
            ResponseBuilder.ok(TransactionResponseMessage(message.externalId!!, result.id.toString()))
        } catch (ex: Exception) {
            when (ex) {
                is ValidationException -> ResponseBuilder.validationFailed(ex.validationMessages)
                else -> ResponseBuilder.internalServerError(ex.message)
            }
        }
    }
}
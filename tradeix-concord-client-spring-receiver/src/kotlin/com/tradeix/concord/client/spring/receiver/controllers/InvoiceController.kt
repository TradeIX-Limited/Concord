package com.tradeix.concord.client.spring.receiver.controllers

import com.tradeix.concord.client.spring.receiver.ResponseBuilder
import com.tradeix.concord.client.spring.receiver.RPCProxy
import com.tradeix.concord.cordapp.flows.invoices.InvoiceIssuanceInitiatorFlow
import com.tradeix.concord.shared.data.VaultRepository
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.messages.TransactionResponseMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceIssuanceRequestMessage
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
    fun issueInvoice(message: InvoiceIssuanceRequestMessage): ResponseEntity<*> {
        return try {
            val flowHandle = proxy.startFlowDynamic(InvoiceIssuanceInitiatorFlow::class.java, message)
            val result = flowHandle.returnValue.getOrThrow()
            ResponseBuilder.ok(TransactionResponseMessage(message.externalId!!, result.id.toString()))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }
}
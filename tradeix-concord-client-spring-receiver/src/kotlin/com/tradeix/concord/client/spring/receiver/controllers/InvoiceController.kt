package com.tradeix.concord.client.spring.receiver.controllers

import com.tradeix.concord.client.spring.receiver.ResponseBuilder
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
class InvoiceController : Controller() {

    private val repository = VaultRepository.fromCordaRPCOps<InvoiceState>(proxy)

    @GetMapping(path = arrayOf("/all"))
    fun getAllInvoiceStatesById(
            @RequestParam(name = "externalId") externalId: String): ResponseEntity<*> {

        return try {
            ResponseBuilder.ok(repository.findByExternalId(externalId, Vault.StateStatus.ALL))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }

    @GetMapping(path = arrayOf("/all"))
    fun getAllInvoiceStates(
            @RequestParam(name = "pageNumber", defaultValue = "1") pageNumber: Int,
            @RequestParam(name = "pageSize", defaultValue = "50") pageSize: Int): ResponseEntity<*> {

        return try {
            ResponseBuilder.ok(repository.getPagedItems(pageNumber, pageSize, Vault.StateStatus.ALL))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }

    @GetMapping(path = arrayOf("/consumed"))
    fun getConsumedInvoiceStatesById(
            @RequestParam(name = "externalId") externalId: String): ResponseEntity<*> {

        return try {
            ResponseBuilder.ok(repository.findByExternalId(externalId, Vault.StateStatus.CONSUMED))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }

    @GetMapping(path = arrayOf("/consumed"))
    fun getConsumedInvoiceStates(
            @RequestParam(name = "pageNumber", defaultValue = "1") pageNumber: Int,
            @RequestParam(name = "pageSize", defaultValue = "50") pageSize: Int): ResponseEntity<*> {

        return try {
            ResponseBuilder.ok(repository.getPagedItems(pageNumber, pageSize, Vault.StateStatus.CONSUMED))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }

    @GetMapping(path = arrayOf("/unconsumed"))
    fun getUnconsumedInvoiceStatesById(
            @RequestParam(name = "externalId") externalId: String): ResponseEntity<*> {

        return try {
            ResponseBuilder.ok(repository.findByExternalId(externalId, Vault.StateStatus.UNCONSUMED))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }


    @GetMapping(path = arrayOf("/unconsumed"))
    fun getUnconsumedInvoiceStates(
            @RequestParam(name = "pageNumber", defaultValue = "1") pageNumber: Int,
            @RequestParam(name = "pageSize", defaultValue = "50") pageSize: Int): ResponseEntity<*> {

        return try {
            ResponseBuilder.ok(repository.getPagedItems(pageNumber, pageSize))
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
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
package com.tradeix.concord.cordapp.supplier.client.receiver.controllers

import com.tradeix.concord.cordapp.supplier.flows.fundingresponses.FundingResponseAcceptanceFlow
import com.tradeix.concord.cordapp.supplier.flows.fundingresponses.FundingResponseRejectionFlow
import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationRequestMessage
import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationResponseMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransactionResponseMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.mappers.FundingResponseResponseMapper
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.domain.states.FundingResponseState
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
@RequestMapping(path = arrayOf("/fundingresponses"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class FundingResponseController(private val rpc: RPCConnectionProvider) {

    private val vaultService = VaultService.fromCordaRPCOps<FundingResponseState>(rpc.proxy)
    private val fundingResponseResponseMapper = FundingResponseResponseMapper()

    companion object {
        private val logger : Logger = loggerFor<FundingResponseController>()
    }

    @GetMapping()
    fun getFundingResponseStates(
            @RequestParam(name = "externalId", required = false) externalId: String?,
            @RequestParam(name = "status", required = false, defaultValue = "unconsumed") status: String,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") pageNumber: Int,
            @RequestParam(name = "pageSize", required = false, defaultValue = "50") pageSize: Int
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val stateStatus = Vault.StateStatus.valueOf(status.toUpperCase())

                if (externalId.isNullOrBlank()) {
                    val fundingResponseStates = vaultService
                            .getPagedItems(pageNumber, pageSize, stateStatus)
                            .map { fundingResponseResponseMapper.map(it.state.data) }

                    ResponseBuilder.ok(fundingResponseStates)
                } else {
                    val fundingResponseStates = vaultService
                            .findByExternalId(externalId!!, pageNumber, pageSize, stateStatus)
                            .map { fundingResponseResponseMapper.map(it.state.data) }

                    ResponseBuilder.ok(fundingResponseStates)
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
    fun getUnconsumedFundingResponseStateByExternalId(
            @PathVariable externalId: String
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val invoice = vaultService
                        .findByExternalId(externalId, status = Vault.StateStatus.UNCONSUMED)
                        .map { fundingResponseResponseMapper.map(it.state.data) }
                        .single()

                ResponseBuilder.ok(invoice)
            } catch (ex: Exception) {
                when (ex) {
                    is IllegalArgumentException -> ResponseBuilder.badRequest(ex.message)
                    else -> ResponseBuilder.internalServerError(ex.message)
                }
            }
        }
    }

    @GetMapping(path = arrayOf("/count"))
    fun getUniqueFundingResponseStateCount(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(mapOf("count" to vaultService.getCount()))
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/hash"))
    fun getMostRecentFundingResponseHash(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(mapOf("hash" to vaultService.getLatestHash()))
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @PutMapping(path = arrayOf("/accept"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun acceptFundingResponse(
            @RequestBody message: FundingResponseConfirmationRequestMessage
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val future = rpc.proxy.startTrackedFlow(::FundingResponseAcceptanceFlow, message)
                future.progress.subscribe { println(it) }
                val result = future.returnValue.getOrThrow()
                val response = FundingResponseConfirmationResponseMessage(
                        externalId = result.tx.outputsOfType<FundingResponseState>().single().linearId.externalId!!,
                        transactionId = result.tx.id.toString()
                )

                Configurator.setLevel(logger.name, Level.DEBUG)
                logger.debug("*** SUCCESSFULLY ACCEPT FUNDING RESPONSE>> Transaction Id: " + response.transactionId)

                ResponseBuilder.ok(response)
            } catch (ex: Exception) {
                when (ex) {
                    is ValidationException -> ResponseBuilder.validationFailed(ex.validationMessages)
                    else -> ResponseBuilder.internalServerError(ex.message)
                }
            }
        }
    }

    @PutMapping(path = arrayOf("/reject"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun rejectFundingResponse(
            @RequestBody message: FundingResponseConfirmationRequestMessage
    ): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val future = rpc.proxy.startTrackedFlow(::FundingResponseRejectionFlow, message)
                future.progress.subscribe { println(it) }
                val result = future.returnValue.getOrThrow()
                val response = FundingResponseConfirmationResponseMessage(
                        externalId = result.tx.outputsOfType<FundingResponseState>().single().linearId.externalId!!,
                        transactionId = result.tx.id.toString()
                )

                Configurator.setLevel(logger.name, Level.DEBUG)
                logger.debug("*** SUCCESSFULLY REJECT FUNDING RESPONSE>> Transaction Id: " + response.transactionId)

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
package com.tradeix.concord.cordapp.funder.client.receiver.controllers

import com.tradeix.concord.cordapp.funder.flows.fundingresponses.FundingResponseIssuanceInitiatorFlow
import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceRequestMessage
import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceResponseMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.mappers.FundingResponseResponseMapper
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.domain.states.FundingResponseState
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
@RequestMapping(path = arrayOf("/fundingresponses"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class FundingResponseController(private val rpc: RPCConnectionProvider) {

    private val vaultService = VaultService.fromCordaRPCOps<FundingResponseState>(rpc.proxy)
    private val fundingResponseResponseMapper = FundingResponseResponseMapper()

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
                    val fundingResponses = vaultService
                            .getPagedItems(pageNumber, pageSize, stateStatus)
                            .map { fundingResponseResponseMapper.map(it.state.data) }

                    ResponseBuilder.ok(fundingResponses)
                } else {
                    val fundingResponses = vaultService
                            .findByExternalId(externalId!!, pageNumber, pageSize, stateStatus)
                            .map { fundingResponseResponseMapper.map(it.state.data) }

                    ResponseBuilder.ok(fundingResponses)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
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
                val fundingResponseRequestMessage = vaultService
                        .findByExternalId(externalId, status = Vault.StateStatus.UNCONSUMED)
                        .map { fundingResponseResponseMapper.map(it.state.data) }
                        .single()

                ResponseBuilder.ok(fundingResponseRequestMessage)
            } catch (ex: Exception) {
                when (ex) {
                    is IllegalArgumentException -> ResponseBuilder.badRequest(ex.message)
                    else -> ResponseBuilder.internalServerError(ex.message)
                }
            }
        }
    }

    @GetMapping(path = arrayOf("/count"))
    fun getUniqueFundingResponseCount(): Callable<ResponseEntity<*>> {
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

    @PostMapping(path = arrayOf("/issue"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun issueFundingResponse(
            @RequestBody message: FundingResponseIssuanceRequestMessage
    ): Callable<ResponseEntity<*>> {

        return Callable {
            try {
                val future = rpc.proxy.startTrackedFlow(::FundingResponseIssuanceInitiatorFlow, message)
                future.progress.subscribe { println(it) }
                val result = future.returnValue.getOrThrow()
                val response = FundingResponseIssuanceResponseMessage(

                        externalId = result.tx.outputsOfType<FundingResponseState>().single().linearId.externalId!!,
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
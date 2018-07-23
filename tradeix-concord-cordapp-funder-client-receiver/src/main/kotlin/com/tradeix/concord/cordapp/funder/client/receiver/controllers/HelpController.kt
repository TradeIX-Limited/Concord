package com.tradeix.concord.cordapp.funder.client.receiver.controllers

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceRequestMessage
import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceResponseMessage
import com.tradeix.concord.cordapp.funder.validators.fundingresponses.FundingResponseIssuanceRequestMessageValidator
import com.tradeix.concord.shared.client.webapi.RequestParameterInfo
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import net.corda.core.crypto.SecureHash
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Callable

@RestController
@RequestMapping(path = arrayOf("/help"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class HelpController {

    @GetMapping(path = arrayOf("/fundingresponses"))
    fun getFundingResponseControllerHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "externalId" to RequestParameterInfo(false),
                                "status" to RequestParameterInfo(false, "unconsumed", listOf("unconsumed", "consumed", "all")),
                                "pageNumber" to RequestParameterInfo(false, "1"),
                                "pageSize" to RequestParameterInfo(false, "50")
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }


    @GetMapping(path = arrayOf("/fundingresponses/count"))
    fun getUniqueFundingResponseCountHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "produces" to mapOf("count" to 123)
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/fundingresponses/hash"))
    fun getMostRecentFundingResponseHashHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "produces" to mapOf("hash" to SecureHash.randomSHA256().bytes)
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/fundingresponses/issue"))
    fun getInvoiceValidationHelp(): Callable<ResponseEntity<*>> {

        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "consumes" to FundingResponseIssuanceRequestMessage(),
                                "produces" to FundingResponseIssuanceResponseMessage(SecureHash.parse("").toString(), ""),
                                "messageValidation" to FundingResponseIssuanceRequestMessageValidator()
                                        .getValidationMessages(),
                                "contractValidation" to FundingResponseContract.Issue()
                                        .getValidationMessages()
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/nodes/all"))
    fun getAllNodesHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "produces" to mapOf("nodes" to listOf("node 1", "node 2"))
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/nodes/peers"))
    fun getPeerNodesHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "produces" to mapOf("nodes" to listOf("node 1", "node 2"))
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/nodes/local"))
    fun getLocalNodeHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "produces" to mapOf("node" to "Local node")
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }
}
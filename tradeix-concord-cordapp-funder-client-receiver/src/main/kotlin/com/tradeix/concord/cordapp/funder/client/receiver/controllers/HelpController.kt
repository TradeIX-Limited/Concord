package com.tradeix.concord.cordapp.funder.client.receiver.controllers

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceRequestMessage
import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceResponseMessage
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceTransactionResponseMessage
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceTransferRequestMessage
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceTransferTransactionRequestMessage
import com.tradeix.concord.cordapp.funder.validators.fundingresponses.FundingResponseIssuanceRequestMessageValidator
import com.tradeix.concord.cordapp.funder.validators.invoices.InvoiceTransferTransactionRequestMessageValidator
import com.tradeix.concord.shared.client.messages.fundingresponses.FundingResponseResponseMessage
import com.tradeix.concord.shared.client.messages.invoices.InvoiceResponseMessage
import com.tradeix.concord.shared.client.webapi.RequestParameterInfo
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import net.corda.core.crypto.SecureHash
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.concurrent.Callable

@RestController
@RequestMapping(path = arrayOf("/help"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class HelpController {

    @GetMapping
    fun getHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            ResponseBuilder.ok(
                    arrayOf(
                            mapOf(
                                    "endpoint" to "/invoices",
                                    "help" to "/help/invoices"
                            ),
                            mapOf(
                                    "endpoint" to "/invoices/externalId",
                                    "help" to "/help/invoices/externalId"
                            ),
                            mapOf(
                                    "endpoint" to "/invoices/count",
                                    "help" to "/help/invoices/count"
                            ),
                            mapOf(
                                    "endpoint" to "/invoices/hash",
                                    "help" to "/help/invoices/hash"
                            ),
                            mapOf(
                                    "endpoint" to "/invoices/transfer",
                                    "help" to "/help/invoices/transfer"
                            ),
                            mapOf(
                                    "endpoint" to "/fundingresponses",
                                    "help" to "/help/fundingresponses"
                            ),
                            mapOf(
                                    "endpoint" to "/fundingresponses/externalId",
                                    "help" to "/help/fundingresponses/externalId"
                            ),
                            mapOf(
                                    "endpoint" to "/fundingresponses/count",
                                    "help" to "/help/fundingresponses/count"
                            ),
                            mapOf(
                                    "endpoint" to "/fundingresponses/hash",
                                    "help" to "/help/fundingresponses/hash"
                            ),
                            mapOf(
                                    "endpoint" to "/fundingresponses/issue",
                                    "help" to "/help/fundingresponses/issue"
                            ),
                            mapOf(
                                    "endpoint" to "/nodes/all",
                                    "help" to "/help/nodes/all"
                            ),
                            mapOf(
                                    "endpoint" to "/nodes/peers",
                                    "help" to "/help/nodes/peers"
                            ),
                            mapOf(
                                    "endpoint" to "/nodes/local",
                                    "help" to "/help/nodes/local"
                            )
                    )
            )
        }
    }

    @GetMapping(path = arrayOf("/invoices"))
    fun getInvoicesHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "parameters" to mapOf(
                                        "externalId" to RequestParameterInfo(false),
                                        "status" to RequestParameterInfo(
                                                false,
                                                "unconsumed",
                                                listOf("unconsumed", "consumed", "all")
                                        ),
                                        "pageNumber" to RequestParameterInfo(false, "1"),
                                        "pageSize" to RequestParameterInfo(false, "50")
                                ),
                                "produces" to listOf(
                                        InvoiceResponseMessage(
                                                externalId = "INVOICE_1",
                                                buyer = "Buyer X500 Name",
                                                buyerCompanyReference = "Albertsons",
                                                supplier = "Supplier X500 Name",
                                                supplierCompanyReference = "Nikrome Ltd",
                                                invoiceNumber = "INV_001",
                                                reference = "INVOICE_REFERENCE",
                                                dueDate = LocalDateTime.now(),
                                                amount = BigDecimal.valueOf(123.45),
                                                totalOutstanding = BigDecimal.valueOf(123.45),
                                                settlementDate = LocalDateTime.now(),
                                                invoiceDate = LocalDateTime.now(),
                                                invoicePayments = BigDecimal.valueOf(123.45),
                                                invoiceDilutions = BigDecimal.valueOf(123.45),
                                                originationNetwork = "NETWORK_1",
                                                currency = "USD",
                                                siteId = "S1",
                                                tradeDate = null,
                                                tradePaymentDate = null
                                        )
                                )
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/invoices/externalId"))
    fun getUnconsumedInvoiceStateByExternalIdHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "url_parameter" to "externalId",
                                "produces" to InvoiceResponseMessage(
                                        externalId = "INVOICE_1",
                                        buyer = "Buyer X500 Name",
                                        buyerCompanyReference = "Albertsons",
                                        supplier = "Supplier X500 Name",
                                        supplierCompanyReference = "Nikrome Ltd",
                                        invoiceNumber = "INV_001",
                                        reference = "INVOICE_REFERENCE",
                                        dueDate = LocalDateTime.now(),
                                        amount = BigDecimal.valueOf(123.45),
                                        totalOutstanding = BigDecimal.valueOf(123.45),
                                        settlementDate = LocalDateTime.now(),
                                        invoiceDate = LocalDateTime.now(),
                                        invoicePayments = BigDecimal.valueOf(123.45),
                                        invoiceDilutions = BigDecimal.valueOf(123.45),
                                        originationNetwork = "NETWORK_1",
                                        currency = "USD",
                                        siteId = "S1",
                                        tradeDate = null,
                                        tradePaymentDate = null
                                )
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/invoices/count"))
    fun getUniqueInvoiceCountHelp(): Callable<ResponseEntity<*>> {
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

    @GetMapping(path = arrayOf("/invoices/hash"))
    fun getMostRecentInvoiceHashHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "produces" to mapOf("hash" to SecureHash.randomSHA256().toString())
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/invoices/transfer"))
    fun getInvoiceTransferHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "consumes" to InvoiceTransferTransactionRequestMessage(
                                        assets = listOf(InvoiceTransferRequestMessage())
                                ),
                                "produces" to InvoiceTransactionResponseMessage(
                                        SecureHash.randomSHA256().toString(),
                                        listOf("INVOICE_1", "INVOICE_2")
                                ),
                                "messageValidation" to InvoiceTransferTransactionRequestMessageValidator()
                                        .getValidationMessages(),
                                "contractValidation" to InvoiceContract.Transfer()
                                        .getValidationMessages()
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/fundingresponses"))
    fun getFundingResponseControllerHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "parameters" to mapOf(
                                        "externalId" to RequestParameterInfo(false),
                                        "status" to RequestParameterInfo(
                                                false,
                                                "unconsumed",
                                                listOf("unconsumed", "consumed", "all")
                                        ),
                                        "pageNumber" to RequestParameterInfo(false, "1"),
                                        "pageSize" to RequestParameterInfo(false, "50")
                                ),
                                "produces" to listOf(
                                        FundingResponseResponseMessage(
                                                "FUNDING_RESPONSE_1",
                                                null,
                                                listOf("INV_001", "INV_002"),
                                                "Supplier X500 Name",
                                                "Funder X500 Name",
                                                BigDecimal.valueOf(123.45),
                                                "GBP",
                                                BigDecimal.valueOf(123.45),
                                                BigDecimal.valueOf(123.45),
                                                BigDecimal.valueOf(123),
                                                "PENDING"
                                        )
                                )
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/fundingresponses/externalId"))
    fun getUnconsumedFundingResponseStateByExternalIdHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "url_parameter" to "externalId",
                                "produces" to FundingResponseResponseMessage(
                                        "FUNDING_RESPONSE_1",
                                        null,
                                        listOf("INV_001", "INV_002"),
                                        "Supplier X500 Name",
                                        "Funder X500 Name",
                                        BigDecimal.valueOf(123.45),
                                        "GBP",
                                        BigDecimal.valueOf(123.45),
                                        BigDecimal.valueOf(123.45),
                                        BigDecimal.valueOf(123),
                                        "PENDING"
                                )

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
                                "produces" to mapOf("hash" to SecureHash.randomSHA256().toString())
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/fundingresponses/issue"))
    fun getFundingResponseIssuanceHelp(): Callable<ResponseEntity<*>> {

        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "consumes" to FundingResponseIssuanceRequestMessage(),
                                "produces" to FundingResponseIssuanceResponseMessage(
                                        SecureHash.randomSHA256().toString(),
                                        "FUNDING_RESPONSE_1"
                                ),
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
                                "produces" to mapOf("nodes" to listOf("notary", "node 1", "node 2"))
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
                                "produces" to mapOf("node" to "local node")
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }
}
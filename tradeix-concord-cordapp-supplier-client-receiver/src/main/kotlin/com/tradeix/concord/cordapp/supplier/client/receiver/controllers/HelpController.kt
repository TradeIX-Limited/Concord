package com.tradeix.concord.cordapp.supplier.client.receiver.controllers

import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationRequestMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.*
import com.tradeix.concord.cordapp.supplier.validators.fundingresponses.FundingResponseConfirmationRequestMessageValidator
import com.tradeix.concord.cordapp.supplier.validators.invoices.InvoiceCancellationTransactionRequestMessageValidator
import com.tradeix.concord.cordapp.supplier.validators.invoices.InvoiceTransactionRequestMessageValidator
import com.tradeix.concord.cordapp.supplier.validators.invoices.InvoiceTransferTransactionRequestMessageValidator
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
                                    "endpoint" to "/invoices/issue",
                                    "help" to "/help/invoices/issue"
                            ),
                            mapOf(
                                    "endpoint" to "/invoices/amend",
                                    "help" to "/help/invoices/amend"
                            ),
                            mapOf(
                                    "endpoint" to "/invoices/transfer",
                                    "help" to "/help/invoices/transfer"
                            ),
                            mapOf(
                                    "endpoint" to "/invoices/cancel",
                                    "help" to "/help/invoices/cancel"
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
                                    "endpoint" to "/fundingresponses/accept",
                                    "help" to "/help/fundingresponses/accept"
                            ),
                            mapOf(
                                    "endpoint" to "/fundingresponses/reject",
                                    "help" to "/help/fundingresponses/reject"
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
    fun getInvoiceControllerHelp(): Callable<ResponseEntity<*>> {
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

    @GetMapping(path = arrayOf("/invoices/externalId"))
    fun getUnconsumedInvoiceStateByExternalIdHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "url_parameter" to mapOf("externalId" to "INVOICE_1"),
                                "produces" to InvoiceResponseMessage(
                                        "INVOICE_1",
                                        "Buyer X500 Name",
                                        "Buyer Company Ref",
                                        "Supplier X500 Name",
                                        "Supplier Company Ref",
                                        "INV_001",
                                        "Reference",
                                        LocalDateTime.now(),
                                        BigDecimal.valueOf(123.45),
                                        BigDecimal.valueOf(123.45),
                                        LocalDateTime.now(),
                                        LocalDateTime.now(),
                                        BigDecimal.valueOf(123.45),
                                        BigDecimal.valueOf(123.45),
                                        "NETWORK_001",
                                        "GBP",
                                        "SITE_001",
                                        LocalDateTime.now(),
                                        LocalDateTime.now()
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
                                "produces" to mapOf("hash" to SecureHash.parse("").toString())
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/invoices/issue"))
    fun getInvoiceIssuanceHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "consumes" to InvoiceTransactionRequestMessage(
                                        assets = listOf(InvoiceRequestMessage())
                                ),
                                "produces" to InvoiceTransactionResponseMessage(SecureHash.parse("").toString(), listOf("inv1")),
                                "messageValidation" to InvoiceTransactionRequestMessageValidator()
                                        .getValidationMessages(),
                                "contractValidation" to InvoiceContract.Issue()
                                        .getValidationMessages()
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/invoices/amend"))
    fun getInvoiceAmendmentHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "consumes" to InvoiceTransactionRequestMessage(
                                        assets = listOf(InvoiceRequestMessage())
                                ),
                                "produces" to InvoiceTransactionResponseMessage(SecureHash.parse("").toString(), listOf("inv1")),
                                "messageValidation" to InvoiceTransactionRequestMessageValidator()
                                        .getValidationMessages(),
                                "contractValidation" to InvoiceContract.Amend()
                                        .getValidationMessages()
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
                                "produces" to InvoiceTransactionResponseMessage(SecureHash.parse("").toString(), listOf("inv1")),
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

    @GetMapping(path = arrayOf("/invoices/cancel"))
    fun getInvoiceCancellationHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "consumes" to InvoiceCancellationTransactionRequestMessage(
                                        assets = listOf(InvoiceCancellationRequestMessage())
                                ),
                                "produces" to InvoiceTransactionResponseMessage(SecureHash.parse("").toString(), listOf("inv1")),
                                "messageValidation" to InvoiceCancellationTransactionRequestMessageValidator()
                                        .getValidationMessages(),
                                "contractValidation" to InvoiceContract.Cancel()
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

    @GetMapping(path = arrayOf("/fundingresponses/externalId"))
    fun getUnconsumedFundingResponseStateByExternalIdHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "url_parameter" to mapOf("externalId" to "FUNDING_RESPONSE_1"),
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
                                        BigDecimal.valueOf(123)
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
                                "produces" to mapOf("hash" to SecureHash.parse("").toString())
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/fundingresponses/accept"))
    fun getFundingResponseAcceptHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "consumes" to FundingResponseConfirmationRequestMessage(),
                                "produces" to InvoiceTransactionResponseMessage(SecureHash.parse("").toString(), listOf("inv1")),
                                "messageValidation" to FundingResponseConfirmationRequestMessageValidator()
                                        .getValidationMessages(),
                                "contractValidation" to FundingResponseContract.Accept().getValidationMessages()
                        )
                )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/fundingresponses/reject"))
    fun getFundingResponseRejectHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "consumes" to FundingResponseConfirmationRequestMessage(),
                                "produces" to InvoiceTransactionResponseMessage(SecureHash.parse("").toString(), listOf("inv1")),
                                "messageValidation" to FundingResponseConfirmationRequestMessageValidator()
                                        .getValidationMessages(),
                                "contractValidation" to FundingResponseContract.Reject().getValidationMessages()
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
package com.tradeix.concord.cordapp.supplier.client.receiver.controllers

import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationRequestMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.*
import com.tradeix.concord.cordapp.supplier.validators.fundingresponses.FundingResponseConfirmationRequestMessageValidator
import com.tradeix.concord.cordapp.supplier.validators.invoices.InvoiceCancellationTransactionRequestMessageValidator
import com.tradeix.concord.cordapp.supplier.validators.invoices.InvoiceTransactionRequestMessageValidator
import com.tradeix.concord.cordapp.supplier.validators.invoices.InvoiceTransferTransactionRequestMessageValidator
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
import java.util.concurrent.Callable


@RestController
@RequestMapping(path = arrayOf("/help"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class HelpController {

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

    /* @GetMapping(path = arrayOf("invoices/externalId")) // TODO : WAIT FOR MATT
     fun getUnconsumedInvoiceStateByExternalIdHelp(): Callable<ResponseEntity<*>> {
         return Callable {
             try {
                 ResponseBuilder.ok(
                         mapOf(
                                 "produces" to InvoiceState(
                                         linearId = UniqueIdentifier(
                                                 "INVOICE_EXTERNAL_ID",
                                                 UUID.fromString("00000000-0000-4000-0000-000000000002")
                                         ),
                                         owner = ,
                                         buyer = Participant( ,""),
                                         supplier = Participant( ,""),
                                         invoiceNumber = "INVOICE NUMBER",
                                         invoiceVersion = "1.0",
                                         submitted = LocalDateTime.now(),
                                         reference = "INVOICE REFERENCE",
                                         dueDate = LocalDateTime.now(),
                                         amount = Amount.fromDecimal(BigDecimal.ONE, Currency.getInstance("GBP")),
                                         totalOutstanding = Amount.fromDecimal(BigDecimal.ONE, Currency.getInstance("GBP")),
                                         settlementDate = LocalDateTime.now(),
                                         invoiceDate = LocalDateTime.now(),
                                         invoicePayments = Amount.fromDecimal(BigDecimal.ZERO, Currency.getInstance("GBP")),
                                         invoiceDilutions = Amount.fromDecimal(BigDecimal.ZERO, Currency.getInstance("GBP")),
                                         originationNetwork = "ORIGINATION NETWORK",
                                         siteId = "SITE ID",
                                         tradeDate = LocalDateTime.now(),
                                         tradePaymentDate = LocalDateTime.now()
                                 ))
                         )
             } catch (ex: Exception) {
                 ResponseBuilder.internalServerError(ex.message)
             }
         }
     }*/

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
                                "produces" to mapOf("hash" to SecureHash.randomSHA256().bytes)
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

    @GetMapping(path = arrayOf("/invoices/changeowner"))
    fun getInvoiceOwnershipChangeHelp(): Callable<ResponseEntity<*>> {
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
                                "contractValidation" to InvoiceContract.ChangeOwner()
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
/*
    @GetMapping(path = arrayOf("fundingresponses/externalId")) // TODO : WAIT FOR MATT
    fun getUnconsumedFundingResponseStateByExternalIdHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "produces" to InvoiceState(
                                        linearId = UniqueIdentifier(
                                                "INVOICE_EXTERNAL_ID",
                                                UUID.fromString("00000000-0000-4000-0000-000000000002")
                                        ),
                                        owner = ,
                                        buyer = Participant( ,"BUYER COMPANY"),
                                        supplier = Participant( ,"SUPPLIER COMPANY"),
                                        invoiceNumber = "INVOICE NUMBER",
                                        invoiceVersion = "1.0",
                                        submitted = LocalDateTime.now(),
                                        reference = "INVOICE REFERENCE",
                                        dueDate = LocalDateTime.now(),
                                        amount = Amount.fromDecimal(BigDecimal.ONE, Currency.getInstance("GBP")),
                                        totalOutstanding = Amount.fromDecimal(BigDecimal.ONE, Currency.getInstance("GBP")),
                                        settlementDate = LocalDateTime.now(),
                                        invoiceDate = LocalDateTime.now(),
                                        invoicePayments = Amount.fromDecimal(BigDecimal.ZERO, Currency.getInstance("GBP")),
                                        invoiceDilutions = Amount.fromDecimal(BigDecimal.ZERO, Currency.getInstance("GBP")),
                                        originationNetwork = "ORIGINATION NETWORK",
                                        siteId = "SITE ID",
                                        tradeDate = LocalDateTime.now(),
                                        tradePaymentDate = LocalDateTime.now()
                                ))
                        )
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }*/

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
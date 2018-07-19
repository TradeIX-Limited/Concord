package com.tradeix.concord.cordapp.supplier.client.receiver.controllers

import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationRequestMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransactionRequestMessage
import com.tradeix.concord.cordapp.supplier.validators.fundingresponses.FundingResponseConfirmationRequestMessageValidator
import com.tradeix.concord.cordapp.supplier.validators.invoices.InvoiceTransactionRequestMessageValidator
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.messages.CancellationRequestMessage
import com.tradeix.concord.shared.messages.CancellationTransactionRequestMessage
import com.tradeix.concord.shared.messages.OwnershipRequestMessage
import com.tradeix.concord.shared.messages.OwnershipTransactionRequestMessage
import com.tradeix.concord.shared.validators.CancellationTransactionRequestMessageValidator
import com.tradeix.concord.shared.validators.OwnershipTransactionRequestMessageValidator
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Callable

@RestController
@RequestMapping(path = arrayOf("/help"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class HelpController {

    @GetMapping(path = arrayOf("/invoices/issue"))
    fun getInvoiceIssuanceHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "messageStructure" to InvoiceTransactionRequestMessage(
                                        assets = listOf(InvoiceRequestMessage())
                                ),
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
                                "messageStructure" to InvoiceTransactionRequestMessage(
                                        assets = listOf(InvoiceRequestMessage())
                                ),
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

    @GetMapping(path = arrayOf("/invoices/cancel"))
    fun getInvoiceCancellationHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "messageStructure" to CancellationTransactionRequestMessage(
                                        assets = listOf(CancellationRequestMessage())
                                ),
                                "messageValidation" to CancellationTransactionRequestMessageValidator()
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

    @GetMapping(path = arrayOf("/invoices/changeowner"))
    fun getInvoiceOwnershipChangeHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "messageStructure" to OwnershipTransactionRequestMessage(
                                        assets = listOf(OwnershipRequestMessage())
                                ),
                                "messageValidation" to OwnershipTransactionRequestMessageValidator()
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

    @GetMapping(path = arrayOf("/fundingresponses/accept"))
    fun getFundingResponseAcceptHelp(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "messageStructure" to FundingResponseConfirmationRequestMessage(),
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
                                "messageStructure" to FundingResponseConfirmationRequestMessage(),
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
}
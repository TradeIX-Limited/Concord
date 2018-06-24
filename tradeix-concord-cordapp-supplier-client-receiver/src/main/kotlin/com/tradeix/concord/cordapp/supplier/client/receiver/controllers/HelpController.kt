package com.tradeix.concord.cordapp.supplier.client.receiver.controllers

import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.messages.*
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseAcceptanceRequestMessage
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRejectionRequestMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.shared.validators.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = arrayOf("/help"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class HelpController {

    @GetMapping(path = arrayOf("/invoices/issue"))
    fun getInvoiceIssuanceHelp(): ResponseEntity<*> {
        return try {
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

    @GetMapping(path = arrayOf("/invoices/amend"))
    fun getInvoiceAmendmentHelp(): ResponseEntity<*> {
        return try {
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

    @GetMapping(path = arrayOf("/invoices/cancel"))
    fun getInvoiceCancellationHelp(): ResponseEntity<*> {
        return try {
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

    @GetMapping(path = arrayOf("/invoices/changeowner"))
    fun getInvoiceOwnershipChangeHelp(): ResponseEntity<*> {
        return try {
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

    @GetMapping(path = arrayOf("/fundingresponse/accept"))
    fun getFundingResponseAcceptHelp(): ResponseEntity<*> {
        return try {
            ResponseBuilder.ok(
                    mapOf(
                            "messageStructure" to FundingResponseAcceptanceRequestMessage(),
                            "messageValidation" to FundingResponseAcceptMessageValidator().getValidationMessages(),
                            "contractValidation" to FundingResponseContract.Accept().getValidationMessages()
                    )
            )
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }

    @GetMapping(path = arrayOf("/fundingresponse/reject"))
    fun getFundingResponseRejectHelp(): ResponseEntity<*> {
        return try {
            ResponseBuilder.ok(
                    mapOf(
                            "messageStructure" to FundingResponseRejectionRequestMessage(),
                            "messageValidation" to FundingResponseRejectMessageValidator().getValidationMessages(),
                            "contractValidation" to FundingResponseContract.Reject().getValidationMessages()
                    )
            )
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }
}
package com.tradeix.concord.client.spring.receiver.controllers

import com.tradeix.concord.client.spring.receiver.ResponseBuilder
import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.messages.invoices.InvoiceIssuanceRequestMessage
import com.tradeix.concord.shared.validators.InvoiceMessageValidator
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = arrayOf("/help"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class HelpController {

    @GetMapping(path = arrayOf("/invoices/issue"))
    fun getInvoiceValidationHelp(): ResponseEntity<*> {
        return try {
            ResponseBuilder.ok(
                    mapOf(
                            "messageStructure" to InvoiceIssuanceRequestMessage(),
                            "messageValidation" to InvoiceMessageValidator().getValidationMessages(),
                            "contractValidation" to InvoiceContract.Commands.Issue().getValidationMessages()
                    )
            )
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }
}
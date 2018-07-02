package com.tradeix.concord.cordapp.funder.client.receiver.controllers

import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRequestMessage
import com.tradeix.concord.shared.validators.FundingResponseRequestMessageValidator
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = arrayOf("/help"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class HelpController {

    @GetMapping(path = arrayOf("/fundingresponses/issue"))
    fun getInvoiceValidationHelp(): ResponseEntity<*> {
        return try {
            ResponseBuilder.ok(
                    mapOf(
                            "messageStructure" to FundingResponseRequestMessage(),
                            "messageValidation" to FundingResponseRequestMessageValidator().getValidationMessages(),
                            "contractValidation" to FundingResponseContract.Issue().getValidationMessages()
                    )
            )
        } catch (ex: Exception) {
            ResponseBuilder.internalServerError(ex.message)
        }
    }
}
package com.tradeix.concord.cordapp.funder.client.receiver.controllers

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceRequestMessage
import com.tradeix.concord.cordapp.funder.validators.fundingresponses.FundingResponseIssuanceRequestMessageValidator
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Callable

@RestController
@RequestMapping(path = arrayOf("/help"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class HelpController {

    @GetMapping(path = arrayOf("/fundingresponses/issue"))
    fun getInvoiceValidationHelp(): Callable<ResponseEntity<*>> {

        return Callable {
            try {
                ResponseBuilder.ok(
                        mapOf(
                                "messageStructure" to FundingResponseIssuanceRequestMessage(),
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
}
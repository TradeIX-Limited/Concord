package com.tradeix.concord.client.spring.receiver.controllers

import com.tradeix.concord.shared.messages.invoices.InvoiceIssuanceRequestMessage
import com.tradeix.concord.shared.messages.TransactionResponseMessage
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = arrayOf("/invoices"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class InvoiceController : Controller() {

    @PostMapping(path = arrayOf("/issue"), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun issueInvoice(message: InvoiceIssuanceRequestMessage): ResponseEntity<TransactionResponseMessage> {
        return ResponseEntity.ok(TransactionResponseMessage("EXID", "TXID"))
    }
}
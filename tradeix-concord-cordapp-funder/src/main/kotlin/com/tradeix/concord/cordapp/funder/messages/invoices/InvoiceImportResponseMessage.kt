package com.tradeix.concord.cordapp.funder.messages.invoices

import com.fasterxml.jackson.annotation.JsonProperty
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceImportResponseMessage(val batchUploadId: String)
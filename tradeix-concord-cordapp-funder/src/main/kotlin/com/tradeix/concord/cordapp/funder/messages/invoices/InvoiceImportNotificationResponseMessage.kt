package com.tradeix.concord.cordapp.funder.messages.invoices

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceImportNotificationResponseMessage(val batchUploadId: String)
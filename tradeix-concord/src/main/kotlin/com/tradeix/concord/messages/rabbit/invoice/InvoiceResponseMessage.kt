package com.tradeix.concord.messages.rabbit.invoice

import com.tradeix.concord.messages.MultiErrorMessage
import com.tradeix.concord.messages.TransactionMessage
import com.tradeix.concord.messages.rabbit.RabbitResponseMessage

class InvoiceResponseMessage(
        override val externalIds: List<String>?,
        override val success: Boolean,
        override val correlationId: String?,
        override val errorMessages: List<String>?,
        override val transactionId: String?
) : RabbitResponseMessage(), MultiErrorMessage, TransactionMessage
package com.tradeix.concord.messages.rabbit

import com.tradeix.concord.messages.MultiErrorMessage
import com.tradeix.concord.messages.TransactionMessage

class RabbitResponseMessage(
        override val correlationId: String,
        override val transactionId: String?,
        override val errorMessages: List<String>?,
        val externalIds: List<String>?,
        val success: Boolean
) : RabbitMessage(correlationId), TransactionMessage, MultiErrorMessage
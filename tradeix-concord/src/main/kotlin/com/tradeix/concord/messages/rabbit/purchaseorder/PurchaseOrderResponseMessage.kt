package com.tradeix.concord.messages.rabbit.purchaseorder

import com.tradeix.concord.messages.MultiErrorMessage
import com.tradeix.concord.messages.TransactionMessage
import com.tradeix.concord.messages.rabbit.RabbitResponseMessage

class PurchaseOrderResponseMessage(
        override val externalIds: List<String>?,
        override val success: Boolean,
        override val correlationId: String?,
        override val errorMessages: List<String>?,
        override val transactionId: String?,
        val bidUniqueId: String?
) : RabbitResponseMessage(), MultiErrorMessage, TransactionMessage
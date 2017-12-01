package com.tradeix.concord.messages.rabbit.tradeasset

import com.tradeix.concord.messages.MultiErrorMessage
import com.tradeix.concord.messages.TransactionMessage
import com.tradeix.concord.messages.rabbit.RabbitResponseMessage

data class TradeAssetOwnershipResponseMessage (
        override val correlationId: String,
        override val transactionId: String?,
        override val errorMessages: List<String>?,
        override val externalIds: List<String>?,
        override val success: Boolean,
        val bidUniqueId: String?
) : RabbitResponseMessage(), TransactionMessage, MultiErrorMessage
package com.tradeix.concord.messages.webapi

import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.TransactionMessage

class SingleIdentitySuccessResponseMessage(
        override val externalId: String,
        override val transactionId: String
) : SingleIdentityMessage, TransactionMessage
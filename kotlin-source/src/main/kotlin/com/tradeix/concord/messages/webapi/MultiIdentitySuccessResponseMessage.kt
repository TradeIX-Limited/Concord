package com.tradeix.concord.messages.webapi

import com.tradeix.concord.messages.MultiIdentityMessage
import com.tradeix.concord.messages.TransactionMessage

class MultiIdentitySuccessResponseMessage(
        override val externalIds: List<String>,
        override val transactionId: String?
) : MultiIdentityMessage, TransactionMessage
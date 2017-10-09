package com.tradeix.concord.messages

data class IssuePurchaseOrderResponseMessage(
        val transactionId: String,
        val linearId: String)
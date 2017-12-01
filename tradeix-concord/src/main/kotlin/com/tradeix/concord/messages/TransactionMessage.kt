package com.tradeix.concord.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
interface TransactionMessage {
    val transactionId: String?
}
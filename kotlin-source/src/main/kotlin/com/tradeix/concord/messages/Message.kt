package com.tradeix.concord.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
abstract class Message{
    abstract val correlationId: String?
    abstract val tryCount: Int
}
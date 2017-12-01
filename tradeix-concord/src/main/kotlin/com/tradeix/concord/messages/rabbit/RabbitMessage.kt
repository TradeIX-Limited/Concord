package com.tradeix.concord.messages.rabbit

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
abstract class RabbitMessage{
    abstract val correlationId: String?
}
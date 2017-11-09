package com.tradeix.concord.messages.rabbit

abstract class RabbitRequestMessage(
        override val correlationId: String?,
        open val tryCount: Int = 1
) : RabbitMessage(correlationId)
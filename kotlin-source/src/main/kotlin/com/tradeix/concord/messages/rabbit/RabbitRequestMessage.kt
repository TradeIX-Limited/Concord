package com.tradeix.concord.messages.rabbit

abstract class RabbitRequestMessage(
        override val correlationId: String?,
        open val tryCount: Int = 0
) : RabbitMessage(correlationId)
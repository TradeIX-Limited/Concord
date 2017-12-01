package com.tradeix.concord.messages.rabbit

abstract class RabbitRequestMessage : RabbitMessage() {
    abstract override val correlationId: String?
    abstract var tryCount: Int
}
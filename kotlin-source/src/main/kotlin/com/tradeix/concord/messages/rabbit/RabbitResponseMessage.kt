package com.tradeix.concord.messages.rabbit

abstract class RabbitResponseMessage : RabbitMessage() {
    abstract val externalIds: List<String>?
    abstract val success: Boolean
}
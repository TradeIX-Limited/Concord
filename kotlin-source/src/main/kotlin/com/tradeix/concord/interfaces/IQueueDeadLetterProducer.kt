package com.tradeix.concord.interfaces

interface IQueueDeadLetterProducer<Message> {
    fun Publish(message: Message, isFatal: Boolean = false)
}
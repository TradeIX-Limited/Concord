package com.tradeix.concord.interfaces

interface IQueueDeadLetterProducer<in Message> {
    fun publish(message: Message, isFatal: Boolean = false)
}
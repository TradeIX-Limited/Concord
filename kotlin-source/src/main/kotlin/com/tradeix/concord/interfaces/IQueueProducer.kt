package com.tradeix.concord.interfaces

interface IQueueProducer<Message> {
    fun publish(message: Message)
}
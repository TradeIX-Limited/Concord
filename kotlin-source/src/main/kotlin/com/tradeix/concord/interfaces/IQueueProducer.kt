package com.tradeix.concord.interfaces

interface IQueueProducer<Message> {
    fun Publish(message: Message)
}
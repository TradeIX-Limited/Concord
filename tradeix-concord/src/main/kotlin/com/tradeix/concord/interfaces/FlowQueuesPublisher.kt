package com.tradeix.concord.interfaces

import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import com.tradeix.concord.services.messaging.RabbitMqConnectionProvider

interface FlowQueuesPublisher {
    fun initialize(connectionProvider: RabbitMqConnectionProvider, currentPublishers: MutableMap<String, IQueueProducer<RabbitRequestMessage>>)
}

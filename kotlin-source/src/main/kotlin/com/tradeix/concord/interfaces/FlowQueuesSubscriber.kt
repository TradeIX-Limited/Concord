package com.tradeix.concord.interfaces

import com.tradeix.concord.services.messaging.RabbitMqConnectionProvider

interface FlowQueuesSubscriber {
    fun initialize(connectionProvider: RabbitMqConnectionProvider, currentConsumers: MutableMap<String, IQueueConsumer>)
}

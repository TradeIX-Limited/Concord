package com.tradeix.concord.services.messaging

data class RabbitDeadLetterConfiguration(
        val exchangeName: String,
        val exchangeType: String,
        val exchangeRoutingKey: String,
        val durableExchange: Boolean,
        val autoDeleteExchange: Boolean,
        val exchangeArguments: Map<String, Any>,
        val queueName: String,
        val durableQueue: Boolean,
        val exclusiveQueue: Boolean,
        val autoDeleteQueue: Boolean,
        var queueArguments: Map<String, Any>,
        val poisonQueueName: String,
        val poisonQueueRoutingKey: String
)

{
    fun getParsedQueueArguments():Map<String, Any> {
        return queueArguments.mapValues {
            if(it.value is Double) {
                (it.value as Double).toInt()
            } else {
                it.value
            }
        }
    }
}
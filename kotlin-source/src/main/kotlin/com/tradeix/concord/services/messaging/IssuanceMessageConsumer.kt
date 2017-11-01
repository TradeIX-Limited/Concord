package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.*
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import java.nio.charset.Charset

class IssuanceMessageConsumer(val channel: Channel) : Consumer {
    override fun handleRecoverOk(consumerTag: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleConsumeOk(consumerTag: String?) {
        println("IssuanceMessageConsumer: handleConsumeOk for consumer tag: $consumerTag")
    }

    override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) {
        println("IssuanceMessageConsumer: handleConsumeOk for consumer tag: $consumerTag")
    }

    override fun handleCancel(consumerTag: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
        val deliveryTag = envelope?.deliveryTag

        // Handler logic here
        val messageBody = body?.toString(Charset.defaultCharset())
        println("Received message $messageBody")
        val serializer = Gson()
        val requestMessage = serializer.fromJson(messageBody, TradeAssetIssuanceRequestMessage::class.java)


        channel.basicAck(deliveryTag!!, false)

    }

    override fun handleCancelOk(consumerTag: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
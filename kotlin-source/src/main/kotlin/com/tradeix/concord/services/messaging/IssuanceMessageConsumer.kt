package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.*
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.interfaces.IQueueProducer
import com.tradeix.concord.messages.Message
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import net.corda.core.identity.CordaX500Name
import java.nio.charset.Charset

class IssuanceMessageConsumer(val channel: Channel, val deadLetterProducer: IQueueDeadLetterProducer<Message>, val maxRetryCount: Int) : Consumer {
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

        var requestMessage = TradeAssetIssuanceRequestMessage(null, 1, null, null, null, CordaX500Name("TradeIX", "London", "GB"), null, null, null, null)


        try {
            requestMessage = serializer.fromJson(messageBody, TradeAssetIssuanceRequestMessage::class.java)
            channel.basicAck(deliveryTag!!, false)


        } catch (ex: Throwable) {
            channel.basicAck(deliveryTag!!, false)
            if(requestMessage.tryCount < maxRetryCount){
                deadLetterProducer.Publish(requestMessage)
            } else {
                deadLetterProducer.Publish(requestMessage, isFatal = true)
            }
        }
    }

    override fun handleCancelOk(consumerTag: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
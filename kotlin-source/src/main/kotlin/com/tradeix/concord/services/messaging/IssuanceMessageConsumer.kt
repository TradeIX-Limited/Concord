package com.tradeix.concord.services.messaging

import com.rabbitmq.client.*

class IssuanceMessageConsumer(val channel: Channel) : Consumer {
    override fun handleRecoverOk(consumerTag: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleConsumeOk(consumerTag: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleCancel(consumerTag: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleDelivery(consumerTag: String?, envelope: Envelope?, properties: AMQP.BasicProperties?, body: ByteArray?) {
        val deliveryTag = envelope?.deliveryTag

        // Handler logic here


        channel.basicAck(deliveryTag!!, false)

    }

    override fun handleCancelOk(consumerTag: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
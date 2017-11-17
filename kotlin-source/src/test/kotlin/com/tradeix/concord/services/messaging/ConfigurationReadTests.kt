package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigParseOptions
import com.typesafe.config.ConfigRenderOptions
import net.corda.nodeapi.config.parseAs
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ConfigurationReadTests{


    companion object {
        val  defaultConfig = ConfigFactory.parseResources("tix.integration.test.conf", ConfigParseOptions.defaults().setAllowMissing(false))
    }

    @Test
    fun `Read connection configuration`(){
        val connectionConfig = defaultConfig!!.resolve().getConfig("tix-integration.rabbitMqConnectionConfiguration").parseAs<RabbitMqConnectionConfiguration>()
        assertEquals("guest", connectionConfig.userName)
        assertEquals("guest", connectionConfig.password)
        assertEquals("localhost", connectionConfig.hostName)
        assertEquals(5672, connectionConfig.portNumber)
        assertEquals("/", connectionConfig.virtualHost)
    }

    @Test
    fun `Read dead letter configuration`(){
        // config library cannot parse into a Map type so need to render as string then use Gson to deserialize.
        val deadLetterConfigurationString = defaultConfig!!.resolve().getConfig("tix-integration").getObject("issuanceDeadLetterConfig").render(ConfigRenderOptions.concise())
        val serializer = Gson()
        val deadLetterConfiguration =  serializer.fromJson(deadLetterConfigurationString, RabbitDeadLetterConfiguration::class.java)

        assertEquals("tixcorda_messaging_dlq", deadLetterConfiguration.exchangeName)
        assertNull(deadLetterConfiguration.exchangeArguments)
        assertEquals("tixcorda_messaging", deadLetterConfiguration.queueArguments["x-dead-letter-exchange"])
        assertEquals(60000.0, deadLetterConfiguration.queueArguments["x-message-ttl"])
        assertFalse { deadLetterConfiguration.autoDeleteExchange }
    }

    @Test
    fun `Read consumer configuration`(){
        val consumerConfigurationString = defaultConfig!!.resolve().getConfig("tix-integration").getObject("issuanceConsumeConfiguration").render(ConfigRenderOptions.concise())
        val serializer = Gson()
        val consumerConfiguration = serializer.fromJson(consumerConfigurationString, RabbitConsumerConfiguration::class.java)

        assertEquals("tixcorda_messaging", consumerConfiguration.exchangeName)
        assertFalse { consumerConfiguration.autoDeleteExchange }
        assertTrue { consumerConfiguration.durableExchange }
        assertEquals(5, consumerConfiguration.maxRetries)
        assertNull(consumerConfiguration.exchangeArguments)
    }
}
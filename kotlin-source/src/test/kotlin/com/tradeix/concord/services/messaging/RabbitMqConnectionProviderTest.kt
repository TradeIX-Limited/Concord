package com.tradeix.concord.services.messaging

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals

class RabbitMqConnectionProviderTest {
    @Ignore
    fun `Get Connection`() {
        val mockConnectionFactory = mock<ConnectionFactory>()
        var mockConnection: Connection? = mock()
        whenever(mockConnectionFactory.newConnection()).thenReturn(mockConnection)
        val connectionProvider = RabbitMqConnectionProvider(mockConnectionFactory)
        val connection = connectionProvider.getConnection()
        assertEquals(mockConnection, connection)
    }
}

class RabbitMqStaticConnectionProviderTest {
    @Test
    fun `Always get the same connection`() {
        val mockConnectionFactory = mock<ConnectionFactory>()
        var mockConnection: Connection? = mock()
        whenever(mockConnectionFactory.newConnection()).thenReturn(mockConnection)
        whenever(mockConnection!!.isOpen).thenReturn(true)
        val connectionProvider = RabbitMqConnectionProvider(mockConnectionFactory)
        val connection = connectionProvider.getConnection()
        assertEquals(mockConnection, connection)

        val secondConnection = connectionProvider.getConnection()
        assertEquals(mockConnection, secondConnection)
    }
}
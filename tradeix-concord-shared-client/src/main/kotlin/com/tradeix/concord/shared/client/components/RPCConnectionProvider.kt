package com.tradeix.concord.shared.client.components

import net.corda.client.rpc.CordaRPCClient
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.utilities.NetworkHostAndPort
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySources(
        PropertySource("classpath:receiver.properties", ignoreResourceNotFound = true),
        PropertySource("classpath:responder.properties", ignoreResourceNotFound = true)
)
class RPCConnectionProvider(
        @Value("\${config.rpc.username}") private val username: String,
        @Value("\${config.rpc.password}") private val password: String,
        @Value("\${config.rpc.host}") private val host: String,
        @Value("\${config.rpc.port}") private val port: Int) {

    companion object {
        private val logger: Logger = loggerFor<RPCConnectionProvider>()
    }

    var safeProxy: CordaRPCOps? = null

    val proxy: CordaRPCOps
        get() {
            while (safeProxy == null) {
                try {
                    val networkHostAndPort = NetworkHostAndPort(host, port)
                    val cordaRPCClient = CordaRPCClient(networkHostAndPort)
                    safeProxy = cordaRPCClient.start(username, password).proxy
                } catch (ex: Exception) {
                    logger.warn("Failed to create RPC connection. Retrying in 1 second.")
                    Thread.sleep(1000)
                }
            }
            logger.info("Successfully created RPC connection.")
            return safeProxy!!
        }
}
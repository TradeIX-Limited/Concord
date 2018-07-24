package com.tradeix.concord.tests.integration

import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import com.tradeix.concord.shared.mockdata.ParticipantType
import net.corda.core.utilities.getOrThrow
import net.corda.testing.driver.DriverParameters
import net.corda.testing.driver.NodeHandle
import net.corda.testing.driver.driver
import net.corda.testing.node.StartedMockNode
import net.corda.testing.node.User
import org.junit.After
import org.junit.Before

abstract class ControllerIntegrationTest {

    protected lateinit var buyer: NodeHandle
    protected lateinit var funder: NodeHandle
    protected lateinit var supplier: NodeHandle

    protected lateinit var rpc: RPCConnectionProvider

    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    fun withDriver(action: () -> Unit) {
        val cordapps = listOf(
                "com.tradeix.concord.shared.domain",
                "com.tradeix.concord.shared.cordapp",
                "com.tradeix.concord.cordapp.supplier",
                "com.tradeix.concord.cordapp.funder"
        )

        driver(DriverParameters(
                startNodesInProcess = true,
                extraCordappPackagesToScan = cordapps)) {

            val user = User("user1", "test", permissions = setOf("ALL"))

            supplier = startNode(providedName = SUPPLIER_1_NAME, rpcUsers = listOf(user)).getOrThrow()
            buyer = startNode(providedName = BUYER_1_NAME, rpcUsers = listOf(user)).getOrThrow()
            funder = startNode(providedName = FUNDER_1_NAME, rpcUsers = listOf(user)).getOrThrow()

            initialize()
            action()
        }
    }

    protected abstract fun configureNode(node: StartedMockNode, type: ParticipantType)

    protected open fun initialize() {
    }
}
package com.tradeix.concord.tests.integration.supplier

import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import org.junit.Test

class SupplierNodeControllerIntegrationTests : ControllerIntegrationTest() {

    @Test
    fun `Can get all supplier nodes`() {
        withDriver {
            val result = getAllNodesSupplier()
            assert(result.nodes.count() == 4)
        }
    }

    @Test
    fun `Can get supplier's peer nodes`() {
        withDriver {
            val result = getPeerNodesSupplier()
            assert(result.nodes.count() == 3)
        }
    }

    @Test
    fun `Can get supplier's local node`() {
        withDriver {
            val result = getLocalNodeSupplier()
            assert(result.node.contains("Supplier 1"))
        }
    }
}
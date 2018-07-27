package com.tradeix.concord.tests.integration.funder

import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import org.junit.Test

class FunderNodeControllerIntegrationTests : ControllerIntegrationTest() {

    @Test
    fun `Can get all funder nodes`() {
        withDriver {
            val result = getAllNodesFunder()
            assert(result.nodes.count() == 4)
        }
    }

    @Test
    fun `Can get funder's peer nodes`() {
        withDriver {
            val result = getPeerNodesFunder()
            assert(result.nodes.count() == 3)
        }
    }

    @Test
    fun `Can get funder's local node`() {
        withDriver {
            val result = getLocalNodeFunder()
            assert(result.node.contains("Funder 1"))
        }
    }
}
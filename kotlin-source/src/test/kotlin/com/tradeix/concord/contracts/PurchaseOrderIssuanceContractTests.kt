package com.tradeix.concord.contracts

import com.tradeix.concord.contracts.PurchaseOrderIssuanceContract.Companion.PURCHASE_ORDER_CONTRACT_ID
import com.tradeix.concord.models.PurchaseOrder
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.finance.POUNDS
import net.corda.testing.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class PurchaseOrderIssuanceContractTests {

    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
    }

    @Test
    fun `transaction must include Issue command`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                output(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MINI_CORP,
                            buyer = MEGA_CORP,
                            supplier = MINI_CORP,
                            conductor = BIG_CORP)
                }
                fails()
                command(MEGA_CORP_PUBKEY, MINI_CORP_PUBKEY, BIG_CORP_PUBKEY) { PurchaseOrderIssuanceContract.Commands.Issue() }
                verifies()
            }
        }
    }

    @Test
    fun `transaction must have no inputs`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MINI_CORP,
                            buyer = MEGA_CORP,
                            supplier = MINI_CORP,
                            conductor = BIG_CORP)
                }
                output(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MINI_CORP,
                            buyer = MEGA_CORP,
                            supplier = MINI_CORP,
                            conductor = BIG_CORP)
                }
                command(MEGA_CORP_PUBKEY) { PurchaseOrderIssuanceContract.Commands.Issue() }
                `fails with`("No inputs should be consumed when issuing a purchase order.")
            }
        }
    }

    @Test
    fun `transaction must have one output`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                output(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MINI_CORP,
                            buyer = MEGA_CORP,
                            supplier = MINI_CORP,
                            conductor = BIG_CORP)
                }
                output(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MINI_CORP,
                            buyer = MEGA_CORP,
                            supplier = MINI_CORP,
                            conductor = BIG_CORP)
                }
                command(MEGA_CORP_PUBKEY, MINI_CORP_PUBKEY) { PurchaseOrderIssuanceContract.Commands.Issue() }
                `fails with`("Only one output states should be created.")
            }
        }
    }

    @Test
    fun `buyer must sign transaction`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                output(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MINI_CORP,
                            buyer = MEGA_CORP,
                            supplier = MINI_CORP,
                            conductor = BIG_CORP)
                }
                command(MEGA_CORP_PUBKEY) { PurchaseOrderIssuanceContract.Commands.Issue() }
                `fails with`("All of the participants must be signers.")
            }
        }
    }

    @Test
    fun `supplier must sign transaction`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                output(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MINI_CORP,
                            buyer = MEGA_CORP,
                            supplier = MINI_CORP,
                            conductor = BIG_CORP)
                }
                command(MINI_CORP_PUBKEY) { PurchaseOrderIssuanceContract.Commands.Issue() }
                `fails with`("All of the participants must be signers.")
            }
        }
    }

    @Test
    fun `conductor must sign transaction`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                output(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MINI_CORP,
                            buyer = MEGA_CORP,
                            supplier = MINI_CORP,
                            conductor = BIG_CORP)
                }
                command(BIG_CORP_PUBKEY) { PurchaseOrderIssuanceContract.Commands.Issue() }
                `fails with`("All of the participants must be signers.")
            }
        }
    }

    @Test
    fun `buyer and supplier are not the same entity`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                output(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MEGA_CORP,
                            buyer = MEGA_CORP,
                            supplier = MEGA_CORP,
                            conductor = BIG_CORP)
                }
                command(MEGA_CORP_PUBKEY, BIG_CORP_PUBKEY) { PurchaseOrderIssuanceContract.Commands.Issue() }
                `fails with`("The buyer and the supplier cannot be the same entity.")
            }
        }
    }
}

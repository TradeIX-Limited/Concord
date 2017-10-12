package com.tradeix.concord.contracts

import com.tradeix.concord.models.PurchaseOrder
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.finance.POUNDS
import net.corda.testing.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

class PurchaseOrderOwnershipContractTests {
    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
    }

    @Test
    fun `Purchase Order Change Owner transaction must have one input`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                output(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(MEGA_CORP_PUBKEY, ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    PurchaseOrderContract.Commands.ChangeOwner()
                }
                `fails with`("Only one input should be consumed when changing owner on a purchase order.")
            }
        }
    }

    @Test
    fun `Purchase Order Change Owner transaction must have one output`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                input(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(MEGA_CORP_PUBKEY, ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    PurchaseOrderContract.Commands.ChangeOwner()
                }
                `fails with`("Only one output state should be created.")
            }
        }
    }

    @Test
    fun `Purchase Order Change Owner transaction owner must sign`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                input(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(MEGA_CORP_PUBKEY) { PurchaseOrderContract.Commands.ChangeOwner() }
                `fails with`("All of the participants must be signers.")
            }
        }
    }

    @Test
    fun `Purchase Order Change Owner transaction buyer must sign`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                input(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(BOB_PUBKEY) { PurchaseOrderContract.Commands.ChangeOwner() }
                `fails with`("All of the participants must be signers.")
            }
        }
    }

    @Test
    fun `Purchase Order Change Owner transaction supplier must sign`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                input(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(ALICE_PUBKEY) { PurchaseOrderContract.Commands.ChangeOwner() }
                `fails with`("All of the participants must be signers.")
            }
        }
    }

    @Test
    fun `Purchase Order Change Owner transaction conductor must sign`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                input(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(BIG_CORP_PUBKEY) {
                    PurchaseOrderContract.Commands.ChangeOwner()
                }
                `fails with`("All of the participants must be signers.")
            }
        }
    }

    @Test
    fun `Purchase Order Change Owner transaction supplier and owner cannot be the same entity`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val purchaseOrder = PurchaseOrder(1.POUNDS)
        ledger {
            transaction {
                input(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(PurchaseOrderContract.PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = linearId,
                            purchaseOrder = purchaseOrder,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    PurchaseOrderContract.Commands.ChangeOwner()
                }
                `fails with`("The supplier and the new owner cannot be the same entity.")
            }
        }
    }
}

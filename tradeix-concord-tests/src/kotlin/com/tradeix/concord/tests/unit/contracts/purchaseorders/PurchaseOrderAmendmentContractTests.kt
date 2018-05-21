package com.tradeix.concord.tests.unit.contracts.purchaseorders

import com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract
import com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract.Companion.PURCHASE_ORDER_CONTRACT_ID
import com.tradeix.concord.tests.unit.contracts.ContractTest
import com.tradeix.concord.tests.utils.TestIdentities.BUYER_1
import com.tradeix.concord.tests.utils.TestIdentities.CONDUCTOR_1
import com.tradeix.concord.tests.utils.TestIdentities.SUPPLIER_1
import com.tradeix.concord.tests.utils.TestStates.PURCHASE_ORDER_STATE
import net.corda.testing.node.ledger
import org.junit.Test

class PurchaseOrderAmendmentContractTests : ContractTest() {

    @Test
    fun `On purchase order amendment the transaction must include the Amend command`() {
        services.ledger {
            transaction {
                input(
                        PURCHASE_ORDER_CONTRACT_ID,
                        PURCHASE_ORDER_STATE
                )
                output(
                        PURCHASE_ORDER_CONTRACT_ID,
                        PURCHASE_ORDER_STATE
                )
                fails()
                command(
                        listOf(BUYER_1.publicKey, SUPPLIER_1.publicKey, CONDUCTOR_1.publicKey),
                        PurchaseOrderContract.Commands.Amend()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On purchase order amendment only one input state must be consumed`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_INPUTS) {
                transaction {
                    input(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    input(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    command(
                            listOf(BUYER_1.publicKey, SUPPLIER_1.publicKey, CONDUCTOR_1.publicKey),
                            PurchaseOrderContract.Commands.Amend()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order amendment only one output state must be created`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_OUTPUTS) {
                transaction {
                    input(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    command(
                            listOf(BUYER_1.publicKey, SUPPLIER_1.publicKey, CONDUCTOR_1.publicKey),
                            PurchaseOrderContract.Commands.Amend()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order amendment the buyer and supplier must not be the same entity`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_ENTITIES) {
                transaction {
                    input(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE.copy(supplier = BUYER_1.party)
                    )
                    command(
                            listOf(BUYER_1.publicKey, SUPPLIER_1.publicKey, CONDUCTOR_1.publicKey),
                            PurchaseOrderContract.Commands.Amend()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order amendment the buyer must be the owner`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_OWNER) {
                transaction {
                    input(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE.copy(owner = SUPPLIER_1.party)
                    )
                    command(
                            listOf(BUYER_1.publicKey, SUPPLIER_1.publicKey, CONDUCTOR_1.publicKey),
                            PurchaseOrderContract.Commands.Amend()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order amendment all participants must sign the transaction (buyer must sign)`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_SIGNERS) {
                transaction {
                    input(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    command(
                            listOf(SUPPLIER_1.publicKey, CONDUCTOR_1.publicKey),
                            PurchaseOrderContract.Commands.Amend()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order amendment all participants must sign the transaction (supplier must sign)`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_SIGNERS) {
                transaction {
                    input(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    command(
                            listOf(BUYER_1.publicKey, CONDUCTOR_1.publicKey),
                            PurchaseOrderContract.Commands.Amend()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order amendment all participants must sign the transaction (conductor must sign)`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_SIGNERS) {
                transaction {
                    input(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    command(
                            listOf(BUYER_1.publicKey, SUPPLIER_1.publicKey),
                            PurchaseOrderContract.Commands.Amend()
                    )
                    verifies()
                }
            }
        }
    }
}
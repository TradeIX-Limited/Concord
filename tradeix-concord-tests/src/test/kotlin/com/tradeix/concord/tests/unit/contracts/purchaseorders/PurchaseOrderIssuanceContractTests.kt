package com.tradeix.concord.tests.unit.contracts.purchaseorders

import com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract
import com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract.Companion.PURCHASE_ORDER_CONTRACT_ID
import com.tradeix.concord.shared.mockdata.MockIdentities.BUYER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockStates.PURCHASE_ORDER_STATE
import com.tradeix.concord.tests.unit.contracts.ContractTest
import net.corda.testing.node.ledger
import org.junit.Test

class PurchaseOrderIssuanceContractTests : ContractTest() {

    @Test
    fun `On purchase order issuance the transaction must include the Issue command`() {
        services.ledger {
            transaction {
                output(
                        PURCHASE_ORDER_CONTRACT_ID,
                        PURCHASE_ORDER_STATE
                )
                fails()
                command(
                        listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                        PurchaseOrderContract.Issue()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On purchase order issuance zero input states must be consumed`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Issue.CONTRACT_RULE_INPUTS) {
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
                            listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            PurchaseOrderContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order issuance only one output state must be created`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Issue.CONTRACT_RULE_OUTPUTS) {
                transaction {
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    command(
                            listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            PurchaseOrderContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order issuance the buyer and supplier must not be the same entity`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Issue.CONTRACT_RULE_ENTITIES) {
                transaction {
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE.copy(supplier = BUYER_1_IDENTITY.party)
                    )
                    command(
                            listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            PurchaseOrderContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order issuance the buyer must be the owner`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Issue.CONTRACT_RULE_OWNER) {
                transaction {
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE.copy(owner = SUPPLIER_1_IDENTITY.party)
                    )
                    command(
                            listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            PurchaseOrderContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order issuance all participants must sign the transaction (buyer must sign)`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    command(
                            listOf(SUPPLIER_1_IDENTITY.publicKey),
                            PurchaseOrderContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On purchase order issuance all participants must sign the transaction (supplier must sign)`() {
        services.ledger {
            assertValidationFails(PurchaseOrderContract.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            PURCHASE_ORDER_CONTRACT_ID,
                            PURCHASE_ORDER_STATE
                    )
                    command(
                            listOf(BUYER_1_IDENTITY.publicKey),
                            PurchaseOrderContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }
}
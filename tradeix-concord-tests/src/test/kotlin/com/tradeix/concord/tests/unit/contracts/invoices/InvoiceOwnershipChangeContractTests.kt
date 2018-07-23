package com.tradeix.concord.tests.unit.contracts.invoices

import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.domain.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.shared.mockdata.MockIdentities.BUYER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockStates.INVOICE_STATE
import com.tradeix.concord.tests.unit.contracts.ContractTest
import net.corda.testing.node.ledger
import org.junit.Test

class InvoiceOwnershipChangeContractTests : ContractTest() {

    @Test
    fun `On invoice ownership change the transaction must include the ChangeOwner command`() {
        services.ledger {
            transaction {
                input(
                        INVOICE_CONTRACT_ID,
                        INVOICE_STATE
                )
                output(
                        INVOICE_CONTRACT_ID,
                        INVOICE_STATE
                )
                fails()
                command(
                        listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                        InvoiceContract.Transfer()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On invoice ownership change only one input state must be consumed`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Transfer.CONTRACT_RULE_INPUTS) {
                transaction {
                    output(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    command(
                            listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            InvoiceContract.Transfer()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice ownership change only one output state must be created`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Transfer.CONTRACT_RULE_OUTPUTS) {
                transaction {
                    input(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    command(
                            listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            InvoiceContract.Transfer()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice ownership change all participants must sign the transaction (buyer must sign)`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Transfer.CONTRACT_RULE_SIGNERS) {
                transaction {
                    input(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    output(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    command(
                            listOf(SUPPLIER_1_IDENTITY.publicKey),
                            InvoiceContract.Transfer()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice ownership change all participants must sign the transaction (supplier must sign)`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Transfer.CONTRACT_RULE_SIGNERS) {
                transaction {
                    input(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    output(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    command(
                            listOf(BUYER_1_IDENTITY.publicKey),
                            InvoiceContract.Transfer()
                    )
                    verifies()
                }
            }
        }
    }
}
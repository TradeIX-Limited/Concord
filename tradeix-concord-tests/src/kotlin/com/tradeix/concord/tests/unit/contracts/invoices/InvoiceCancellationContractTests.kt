package com.tradeix.concord.tests.unit.contracts.invoices

import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.domain.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.tests.unit.contracts.ContractTest
import com.tradeix.concord.tests.utils.TestIdentities.BUYER_1
import com.tradeix.concord.tests.utils.TestIdentities.CONDUCTOR_1
import com.tradeix.concord.tests.utils.TestIdentities.SUPPLIER_1
import com.tradeix.concord.tests.utils.TestStates.INVOICE_STATE
import net.corda.testing.node.ledger
import org.junit.Test

class InvoiceCancellationContractTests : ContractTest() {

    @Test
    fun `On invoice cancellation the transaction must include the Cancel command`() {
        services.ledger {
            transaction {
                input(
                        INVOICE_CONTRACT_ID,
                        INVOICE_STATE
                )
                fails()
                command(
                        listOf(BUYER_1.publicKey, SUPPLIER_1.publicKey, CONDUCTOR_1.publicKey),
                        InvoiceContract.Commands.Cancel()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On invoice cancellation only one input state must be consumed`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Commands.Cancel.CONTRACT_RULE_INPUTS) {
                transaction {
                    input(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    input(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    command(
                            listOf(BUYER_1.publicKey, SUPPLIER_1.publicKey, CONDUCTOR_1.publicKey),
                            InvoiceContract.Commands.Cancel()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice cancellation zero output states must be created`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Commands.Cancel.CONTRACT_RULE_OUTPUTS) {
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
                            listOf(BUYER_1.publicKey, SUPPLIER_1.publicKey, CONDUCTOR_1.publicKey),
                            InvoiceContract.Commands.Cancel()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice cancellation all participants must sign the transaction (buyer must sign)`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Commands.Cancel.CONTRACT_RULE_SIGNERS) {
                transaction {
                    input(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    command(
                            listOf(SUPPLIER_1.publicKey, CONDUCTOR_1.publicKey),
                            InvoiceContract.Commands.Cancel()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice cancellation all participants must sign the transaction (supplier must sign)`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Commands.Cancel.CONTRACT_RULE_SIGNERS) {
                transaction {
                    input(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    command(
                            listOf(BUYER_1.publicKey, CONDUCTOR_1.publicKey),
                            InvoiceContract.Commands.Cancel()
                    )
                    verifies()
                }
            }
        }
    }
}
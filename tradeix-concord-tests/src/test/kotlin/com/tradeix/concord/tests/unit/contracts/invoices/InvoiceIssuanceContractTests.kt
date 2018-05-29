package com.tradeix.concord.tests.unit.contracts.invoices

import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.domain.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.shared.mockdata.MockIdentities.BUYER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockStates.INVOICE_STATE
import com.tradeix.concord.tests.unit.contracts.ContractTest
import net.corda.testing.node.ledger
import org.junit.Test

class InvoiceIssuanceContractTests : ContractTest() {

    @Test
    fun `On invoice issuance the transaction must include the Issue command`() {
        services.ledger {
            transaction {
                output(
                        INVOICE_CONTRACT_ID,
                        INVOICE_STATE
                )
                fails()
                command(
                        listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                        InvoiceContract.Commands.Issue()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On invoice issuance zero input states must be consumed`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Commands.Issue.CONTRACT_RULE_INPUTS) {
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
                            listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            InvoiceContract.Commands.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice issuance only one output state must be created`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Commands.Issue.CONTRACT_RULE_OUTPUTS) {
                transaction {
                    output(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    output(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    command(
                            listOf(BUYER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            InvoiceContract.Commands.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice issuance all participants must sign the transaction (buyer must sign)`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Commands.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    command(
                            listOf(SUPPLIER_1_IDENTITY.publicKey),
                            InvoiceContract.Commands.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice issuance all participants must sign the transaction (supplier must sign)`() {
        services.ledger {
            assertValidationFails(InvoiceContract.Commands.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            INVOICE_CONTRACT_ID,
                            INVOICE_STATE
                    )
                    command(
                            listOf(BUYER_1_IDENTITY.publicKey),
                            InvoiceContract.Commands.Issue()
                    )
                    verifies()
                }
            }
        }
    }
}
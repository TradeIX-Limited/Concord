package com.tradeix.concord.tests.unit.contracts.eligibility

import com.tradeix.concord.shared.domain.contracts.InvoiceEligibilityContract
import com.tradeix.concord.shared.domain.contracts.InvoiceEligibilityContract.Companion.INVOICE_ELIGIBILITY_CONTRACT_ID
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockStates.INVOICE_ELIGIBILITY_STATE
import com.tradeix.concord.tests.unit.contracts.ContractTest
import net.corda.testing.node.ledger
import org.junit.Test

class InvoiceEligibilityIssuanceContractTests : ContractTest() {

    @Test
    fun `On invoice eligibility issuance the transaction must include the issue command`() {
        services.ledger {
            transaction {
                output(
                        INVOICE_ELIGIBILITY_CONTRACT_ID,
                        INVOICE_ELIGIBILITY_STATE
                )
                fails()
                command(
                        listOf(SUPPLIER_1_IDENTITY.publicKey, FUNDER_1_IDENTITY.publicKey),
                        InvoiceEligibilityContract.Issue()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On invoice eligibility issuance zero input states must be consumed`() {
        services.ledger {
            assertValidationFails(InvoiceEligibilityContract.Issue.CONTRACT_RULE_INPUTS) {
                transaction {
                    input(
                            INVOICE_ELIGIBILITY_CONTRACT_ID,
                            INVOICE_ELIGIBILITY_STATE
                    )
                    output(
                            INVOICE_ELIGIBILITY_CONTRACT_ID,
                            INVOICE_ELIGIBILITY_STATE
                    )
                    command(
                            listOf(SUPPLIER_1_IDENTITY.publicKey, FUNDER_1_IDENTITY.publicKey),
                            InvoiceEligibilityContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice eligibility issuance at least one output state must be created`() {
        services.ledger {
            transaction {
                output(
                        INVOICE_ELIGIBILITY_CONTRACT_ID,
                        INVOICE_ELIGIBILITY_STATE
                )
                command(
                        listOf(SUPPLIER_1_IDENTITY.publicKey, FUNDER_1_IDENTITY.publicKey),
                        InvoiceEligibilityContract.Issue()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On invoice eligibility issuance all participants must sign the transaction (funder must sign)`() {
        services.ledger {
            assertValidationFails(InvoiceEligibilityContract.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            INVOICE_ELIGIBILITY_CONTRACT_ID,
                            INVOICE_ELIGIBILITY_STATE
                    )
                    command(
                            listOf(SUPPLIER_1_IDENTITY.publicKey),
                            InvoiceEligibilityContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice eligibility issuance all participants must sign the transaction (supplier must sign)`() {
        services.ledger {
            assertValidationFails(InvoiceEligibilityContract.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            INVOICE_ELIGIBILITY_CONTRACT_ID,
                            INVOICE_ELIGIBILITY_STATE
                    )
                    command(
                            listOf(FUNDER_1_IDENTITY.publicKey),
                            InvoiceEligibilityContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }
}
package com.tradeix.concord.tests.unit.contracts.eligibility

import com.tradeix.concord.shared.domain.contracts.InvoiceEligibilityContract
import com.tradeix.concord.shared.domain.contracts.InvoiceEligibilityContract.Companion.INVOICE_ELIGIBILITY_CONTRACT_ID
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockStates.INVOICE_ELIGIBILITY_STATE
import com.tradeix.concord.tests.unit.contracts.ContractTest
import net.corda.testing.node.ledger
import org.junit.Test

class InvoiceEligibilityAmendmentContractTests : ContractTest() {

    @Test
    fun `On invoice eligibility amendment the transaction must include the amend command`() {
        services.ledger {
            transaction {
                input(
                        INVOICE_ELIGIBILITY_CONTRACT_ID,
                        INVOICE_ELIGIBILITY_STATE
                )
                output(
                        INVOICE_ELIGIBILITY_CONTRACT_ID,
                        INVOICE_ELIGIBILITY_STATE
                )
                fails()
                command(
                        listOf(SUPPLIER_1_IDENTITY.publicKey, FUNDER_1_IDENTITY.publicKey),
                        InvoiceEligibilityContract.Amend()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On invoice eligibility amendment at leat one input state must be consumed`() {
        services.ledger {
            assertValidationFails(InvoiceEligibilityContract.Amend.CONTRACT_RULE_INPUTS) {
                transaction {
                    output(
                            INVOICE_ELIGIBILITY_CONTRACT_ID,
                            INVOICE_ELIGIBILITY_STATE
                    )
                    command(
                            listOf(SUPPLIER_1_IDENTITY.publicKey, FUNDER_1_IDENTITY.publicKey),
                            InvoiceEligibilityContract.Amend()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice eligibility amendment at least one output state must be created`() {
        services.ledger {
            assertValidationFails(InvoiceEligibilityContract.Amend.CONTRACT_RULE_OUTPUTS) {
                transaction {
                    input(
                            INVOICE_ELIGIBILITY_CONTRACT_ID,
                            INVOICE_ELIGIBILITY_STATE
                    )
                    command(
                            listOf(SUPPLIER_1_IDENTITY.publicKey, FUNDER_1_IDENTITY.publicKey),
                            InvoiceEligibilityContract.Amend()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice eligibility amendment the number of inputs and outputs must be equal`() {
        services.ledger {
            assertValidationFails(InvoiceEligibilityContract.Amend.CONTRACT_RULE_INPUTS_OUTPUTS) {
                transaction {
                    input(
                            INVOICE_ELIGIBILITY_CONTRACT_ID,
                            INVOICE_ELIGIBILITY_STATE
                    )
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
                            InvoiceEligibilityContract.Amend()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice eligibility amendment all participants must sign te transaction (funder ust sign)`() {
        services.ledger {
            assertValidationFails(InvoiceEligibilityContract.Amend.CONTRACT_RULE_SIGNERS) {
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
                            listOf(SUPPLIER_1_IDENTITY.publicKey),
                            InvoiceEligibilityContract.Amend()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On invoice eligibility amendment all participants must sign te transaction (supplier ust sign)`() {
        services.ledger {
            assertValidationFails(InvoiceEligibilityContract.Amend.CONTRACT_RULE_SIGNERS) {
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
                            listOf(FUNDER_1_IDENTITY.publicKey),
                            InvoiceEligibilityContract.Amend()
                    )
                    verifies()
                }
            }
        }
    }
}
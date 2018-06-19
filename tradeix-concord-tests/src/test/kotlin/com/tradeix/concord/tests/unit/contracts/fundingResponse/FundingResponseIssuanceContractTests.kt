package com.tradeix.concord.tests.unit.contracts.fundingResponse

import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract.Companion.FundingResponse_CONTRACT_ID
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockStates.FUNDING_RESPONSE_STATE
import com.tradeix.concord.tests.unit.contracts.ContractTest
import net.corda.testing.node.ledger
import org.junit.Test

class FundingResponseIssuanceContractTests : ContractTest() {

    @Test
    fun `On funding response issuance the transaction must include the Issue command`() {
        services.ledger {
            transaction {
                output(
                        FundingResponse_CONTRACT_ID,
                        FUNDING_RESPONSE_STATE
                )
                fails()
                command(
                        listOf(FUNDER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                        FundingResponseContract.Issue()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On funding response issuance zero input states must be consumed`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Issue.CONTRACT_RULE_INPUTS) {
                transaction {
                    input(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    command(
                            listOf(FUNDER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            FundingResponseContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On funding response issuance only one output state must be created`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Issue.CONTRACT_RULE_OUTPUTS) {
                transaction {
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    command(
                            listOf(FUNDER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            FundingResponseContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On funding response issuance the status should be set to PENDING`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Issue.CONTRACT_RULE_STATUS) {
                transaction {
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE.accept()
                    )
                    command(
                            listOf(FUNDER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            FundingResponseContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On funding response issuance there must be at least one referenced invoice`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Issue.CONTRACT_RULE_INVOICE_COUNT) {
                transaction {
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE.copy(invoiceLinearIds = emptyList())
                    )
                    command(
                            listOf(FUNDER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            FundingResponseContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On funding response issuance all participants must sign the transaction (funder must sign)`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    command(
                            listOf(SUPPLIER_1_IDENTITY.publicKey),
                            FundingResponseContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On funding response issuance all participants must sign the transaction (supplier must sign)`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    command(
                            listOf(FUNDER_1_IDENTITY.publicKey),
                            FundingResponseContract.Issue()
                    )
                    verifies()
                }
            }
        }
    }
}
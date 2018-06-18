package com.tradeix.concord.tests.unit.contracts.fundingResponse

import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract.Companion.FundingResponse_CONTRACT_ID
import com.tradeix.concord.shared.mockdata.MockIdentities.BUYER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockStates.FUNDING_RESPONSE_STATE_PENDING
import com.tradeix.concord.tests.unit.contracts.ContractTest
import net.corda.testing.node.ledger
import org.junit.Test

class FundingResponseIssuanceContractTests : ContractTest() {

    @Test
    fun `On Funding Response issuance the transaction must include the Issue command`() {
        services.ledger {
            transaction {
                output(
                        FundingResponse_CONTRACT_ID,
                        FUNDING_RESPONSE_STATE_PENDING
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
    fun `On Funding Response issuance zero input states must be consumed`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Issue.CONTRACT_RULE_INPUTS) {
                transaction {
                    input(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE_PENDING
                    )
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE_PENDING
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
    fun `On Funding Response issuance at least one output state must be created`() {
        services.ledger {
            transaction {
                output(
                        FundingResponse_CONTRACT_ID,
                        FUNDING_RESPONSE_STATE_PENDING
                )
                command(
                        listOf(FUNDER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                        FundingResponseContract.Issue()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On Funding Response issuance all participants must sign the transaction (funder must sign)`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE_PENDING
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
    fun `On Funding Response issuance all participants must sign the transaction (supplier must sign)`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE_PENDING
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
    //TODO: Write Unit tests for Status

}
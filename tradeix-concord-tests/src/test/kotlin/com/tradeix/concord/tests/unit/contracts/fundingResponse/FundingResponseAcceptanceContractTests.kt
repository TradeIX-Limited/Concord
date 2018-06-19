package com.tradeix.concord.tests.unit.contracts.fundingResponse

import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import com.tradeix.concord.shared.domain.contracts.FundingResponseContract.Companion.FundingResponse_CONTRACT_ID
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockStates.FUNDING_RESPONSE_STATE
import com.tradeix.concord.tests.unit.contracts.ContractTest
import net.corda.testing.node.ledger
import org.junit.Test

class FundingResponseAcceptanceContractTests : ContractTest() {

    @Test
    fun `On funding response acceptance the transaction must include the Accept command`() {
        services.ledger {
            transaction {
                input(
                        FundingResponse_CONTRACT_ID,
                        FUNDING_RESPONSE_STATE
                )
                output(
                        FundingResponse_CONTRACT_ID,
                        FUNDING_RESPONSE_STATE.accept()
                )
                fails()
                command(
                        listOf(FUNDER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                        FundingResponseContract.Accept()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On funding response acceptance only one input state must be consumed`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Accept.CONTRACT_RULE_INPUTS) {
                transaction {
                    input(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    input(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE.accept()
                    )
                    command(
                            listOf(FUNDER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            FundingResponseContract.Accept()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On funding response acceptance only one output state must be created`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Accept.CONTRACT_RULE_OUTPUTS) {
                transaction {
                    input(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    command(
                            listOf(FUNDER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            FundingResponseContract.Accept()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On funding response acceptance the input state status should be set to PENDING`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Accept.CONTRACT_RULE_INPUT_STATUS) {
                transaction {
                    input(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE.accept()
                    )
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE.accept()
                    )
                    command(
                            listOf(FUNDER_1_IDENTITY.publicKey, SUPPLIER_1_IDENTITY.publicKey),
                            FundingResponseContract.Accept()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On funding response acceptance the output state status should be set to ACCEPTED`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Accept.CONTRACT_RULE_OUTPUT_STATUS) {
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
                            FundingResponseContract.Accept()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On Funding Response acceptance all participants must sign the transaction (funder must sign)`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Accept.CONTRACT_RULE_SIGNERS) {
                transaction {
                    input(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE.accept()
                    )
                    command(
                            listOf(SUPPLIER_1_IDENTITY.publicKey),
                            FundingResponseContract.Accept()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On Funding Response acceptance all participants must sign the transaction (supplier must sign)`() {
        services.ledger {
            assertValidationFails(FundingResponseContract.Accept.CONTRACT_RULE_SIGNERS) {
                transaction {
                    input(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE
                    )
                    output(
                            FundingResponse_CONTRACT_ID,
                            FUNDING_RESPONSE_STATE.accept()
                    )
                    command(
                            listOf(FUNDER_1_IDENTITY.publicKey),
                            FundingResponseContract.Accept()
                    )
                    verifies()
                }
            }
        }
    }
}
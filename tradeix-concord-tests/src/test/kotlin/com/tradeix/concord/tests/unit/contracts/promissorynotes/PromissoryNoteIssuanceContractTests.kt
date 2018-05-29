package com.tradeix.concord.tests.unit.contracts.promissorynotes

import com.tradeix.concord.shared.domain.contracts.PromissoryNoteContract
import com.tradeix.concord.shared.domain.contracts.PromissoryNoteContract.Companion.PROMISSORY_NOTE_CONTRACT_ID
import com.tradeix.concord.shared.mockdata.MockIdentities.GUARANTOR_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.OBLIGEE_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.OBLIGOR_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockStates.PROMISSORY_NOTE_STATE
import com.tradeix.concord.tests.unit.contracts.ContractTest
import net.corda.testing.node.ledger
import org.junit.Test

class PromissoryNoteIssuanceContractTests : ContractTest() {

    @Test
    fun `On promissory note issuance the transaction must include the Issue command`() {
        services.ledger {
            transaction {
                output(
                        PROMISSORY_NOTE_CONTRACT_ID,
                        PROMISSORY_NOTE_STATE
                )
                fails()
                command(
                        listOf(OBLIGOR_1_IDENTITY.publicKey, OBLIGEE_1_IDENTITY.publicKey, GUARANTOR_1_IDENTITY.publicKey),
                        PromissoryNoteContract.Commands.Issue()
                )
                verifies()
            }
        }
    }

    @Test
    fun `On promissory note issuance zero input states must be consumed`() {
        services.ledger {
            assertValidationFails(PromissoryNoteContract.Commands.Issue.CONTRACT_RULE_INPUTS) {
                transaction {
                    input(
                            PROMISSORY_NOTE_CONTRACT_ID,
                            PROMISSORY_NOTE_STATE
                    )
                    output(
                            PROMISSORY_NOTE_CONTRACT_ID,
                            PROMISSORY_NOTE_STATE
                    )
                    command(
                            listOf(OBLIGOR_1_IDENTITY.publicKey, OBLIGEE_1_IDENTITY.publicKey, GUARANTOR_1_IDENTITY.publicKey),
                            PromissoryNoteContract.Commands.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On promissory note issuance only one output state must be created`() {
        services.ledger {
            assertValidationFails(PromissoryNoteContract.Commands.Issue.CONTRACT_RULE_OUTPUTS) {
                transaction {
                    output(
                            PROMISSORY_NOTE_CONTRACT_ID,
                            PROMISSORY_NOTE_STATE
                    )
                    output(
                            PROMISSORY_NOTE_CONTRACT_ID,
                            PROMISSORY_NOTE_STATE
                    )
                    command(
                            listOf(OBLIGOR_1_IDENTITY.publicKey, OBLIGEE_1_IDENTITY.publicKey, GUARANTOR_1_IDENTITY.publicKey),
                            PromissoryNoteContract.Commands.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On promissory note issuance all participants must sign the transaction (obligor must sign)`() {
        services.ledger {
            assertValidationFails(PromissoryNoteContract.Commands.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            PROMISSORY_NOTE_CONTRACT_ID,
                            PROMISSORY_NOTE_STATE
                    )
                    command(
                            listOf(OBLIGEE_1_IDENTITY.publicKey, GUARANTOR_1_IDENTITY.publicKey),
                            PromissoryNoteContract.Commands.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On promissory note issuance all participants must sign the transaction (obligee must sign)`() {
        services.ledger {
            assertValidationFails(PromissoryNoteContract.Commands.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            PROMISSORY_NOTE_CONTRACT_ID,
                            PROMISSORY_NOTE_STATE
                    )
                    command(
                            listOf(OBLIGOR_1_IDENTITY.publicKey, GUARANTOR_1_IDENTITY.publicKey),
                            PromissoryNoteContract.Commands.Issue()
                    )
                    verifies()
                }
            }
        }
    }

    @Test
    fun `On promissory note issuance all participants must sign the transaction (guarantor must sign)`() {
        services.ledger {
            assertValidationFails(PromissoryNoteContract.Commands.Issue.CONTRACT_RULE_SIGNERS) {
                transaction {
                    output(
                            PROMISSORY_NOTE_CONTRACT_ID,
                            PROMISSORY_NOTE_STATE
                    )
                    command(
                            listOf(OBLIGOR_1_IDENTITY.publicKey, OBLIGEE_1_IDENTITY.publicKey),
                            PromissoryNoteContract.Commands.Issue()
                    )
                    verifies()
                }
            }
        }
    }
}
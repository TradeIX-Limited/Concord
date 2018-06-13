package com.tradeix.concord.tests.unit.validators.contracts

import com.tradeix.concord.shared.domain.contracts.PromissoryNoteContract
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PromissoryNoteContractValidationTests {

    @Test
    fun `Issue command produces the expected validation responses`() {
        val contractCommand = PromissoryNoteContract.Issue()
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()
        val expectedValidationMessages = listOf(
                PromissoryNoteContract.Issue.CONTRACT_RULE_INPUTS,
                PromissoryNoteContract.Issue.CONTRACT_RULE_OUTPUTS,
                PromissoryNoteContract.Issue.CONTRACT_RULE_ENTITIES,
                PromissoryNoteContract.Issue.CONTRACT_RULE_SIGNERS
        )

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }
}
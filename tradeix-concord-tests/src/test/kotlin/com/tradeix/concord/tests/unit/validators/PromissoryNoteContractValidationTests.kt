package com.tradeix.concord.tests.unit.validators

import com.tradeix.concord.shared.domain.contracts.PromissoryNoteContract
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PromissoryNoteContractValidationTests {

    @Test
    fun `Issue command produces the expected validation responses`() {
        val contractCommand = PromissoryNoteContract.Issue()
        val expectedValidationMessages = listOf(
                PromissoryNoteContract.Issue.CONTRACT_RULE_INPUTS,
                PromissoryNoteContract.Issue.CONTRACT_RULE_OUTPUTS,
                PromissoryNoteContract.Issue.CONTRACT_RULE_ENTITIES,
                PromissoryNoteContract.Issue.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue {
                actualValidationMessages.contains(it)
            }
        }
    }
}
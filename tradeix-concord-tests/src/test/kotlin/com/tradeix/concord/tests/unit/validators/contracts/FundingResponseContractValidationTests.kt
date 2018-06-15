package com.tradeix.concord.tests.unit.validators.contracts

import com.tradeix.concord.shared.domain.contracts.FundingResponseContract
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FundingResponseContractValidationTests {

    @Test
    fun `Issue FundingResponseContract command produces the expected validation responses`() {
        val contractCommand = FundingResponseContract.Issue()
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()
        val expectedValidationMessages = listOf(
                FundingResponseContract.Issue.CONTRACT_RULE_INPUTS,
                FundingResponseContract.Issue.CONTRACT_RULE_OUTPUTS,
                FundingResponseContract.Issue.CONTRACT_RULE_SIGNERS
        )

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }

    @Test
    fun `Accept FundingResponseContract command produces the expected validation responses`() {
        val contractCommand = FundingResponseContract.Accept()
        val expectedValidationMessages = listOf(
                FundingResponseContract.Accept.CONTRACT_RULE_INPUTS,
                FundingResponseContract.Accept.CONTRACT_RULE_OUTPUTS,
                FundingResponseContract.Accept.CONTRACT_RULE_INPUTS_OUTPUTS,
                FundingResponseContract.Accept.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }


    @Test
    fun `Reject command produces the expected validation responses`() {
        val contractCommand = FundingResponseContract.Reject()
        val expectedValidationMessages = listOf(
                FundingResponseContract.Reject.CONTRACT_RULE_INPUTS,
                FundingResponseContract.Reject.CONTRACT_RULE_OUTPUTS,
                FundingResponseContract.Reject.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }
}
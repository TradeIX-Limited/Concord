package com.tradeix.concord.tests.unit.validators.contracts

import com.tradeix.concord.shared.domain.contracts.InvoiceEligibilityContract
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvoiceEligibilityContractValidationTests {

    @Test
    fun `Issue command produces the expected validation messages`() {
        val contractCommand = InvoiceEligibilityContract.Issue()
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()
        val expectedValidationMessages = listOf(
                InvoiceEligibilityContract.Issue.CONTRACT_RULE_INPUTS,
                InvoiceEligibilityContract.Issue.CONTRACT_RULE_OUTPUTS,
                InvoiceEligibilityContract.Issue.CONTRACT_RULE_SIGNERS
        )

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }

    @Test
    fun `Amend command produces the expected validation messages`() {
        val contractCommand = InvoiceEligibilityContract.Amend()
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()
        val expectedValidationMessages = listOf(
                InvoiceEligibilityContract.Amend.CONTRACT_RULE_INPUTS,
                InvoiceEligibilityContract.Amend.CONTRACT_RULE_OUTPUTS,
                InvoiceEligibilityContract.Amend.CONTRACT_RULE_INPUTS_OUTPUTS,
                InvoiceEligibilityContract.Amend.CONTRACT_RULE_SIGNERS
        )

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }
}
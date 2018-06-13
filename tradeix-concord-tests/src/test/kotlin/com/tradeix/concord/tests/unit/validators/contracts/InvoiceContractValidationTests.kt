package com.tradeix.concord.tests.unit.validators.contracts

import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvoiceContractValidationTests {

    @Test
    fun `Issue command produces the expected validation responses`() {
        val contractCommand = InvoiceContract.Issue()
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()
        val expectedValidationMessages = listOf(
                InvoiceContract.Issue.CONTRACT_RULE_INPUTS,
                InvoiceContract.Issue.CONTRACT_RULE_OUTPUTS,
                InvoiceContract.Issue.CONTRACT_RULE_SIGNERS
        )

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }

    @Test
    fun `Amend command produces the expected validation responses`() {
        val contractCommand = InvoiceContract.Amend()
        val expectedValidationMessages = listOf(
                InvoiceContract.Amend.CONTRACT_RULE_INPUTS,
                InvoiceContract.Amend.CONTRACT_RULE_OUTPUTS,
                InvoiceContract.Amend.CONTRACT_RULE_INPUTS_OUTPUTS,
                InvoiceContract.Amend.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }

    @Test
    fun `ChangeOwner command produces the expected validation responses`() {
        val contractCommand = InvoiceContract.ChangeOwner()
        val expectedValidationMessages = listOf(
                InvoiceContract.ChangeOwner.CONTRACT_RULE_INPUTS,
                InvoiceContract.ChangeOwner.CONTRACT_RULE_OUTPUTS,
                InvoiceContract.ChangeOwner.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }

    @Test
    fun `Cancel command produces the expected validation responses`() {
        val contractCommand = InvoiceContract.Cancel()
        val expectedValidationMessages = listOf(
                InvoiceContract.Cancel.CONTRACT_RULE_INPUTS,
                InvoiceContract.Cancel.CONTRACT_RULE_OUTPUTS,
                InvoiceContract.Cancel.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }
}
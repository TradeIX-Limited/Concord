package com.tradeix.concord.tests.unit.validators

import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvoiceContractValidationTests {

    @Test
    fun `Issue command produces the expected validation responses`() {
        val contractCommand = InvoiceContract.Commands.Issue()
        val expectedValidationMessages = listOf(
                InvoiceContract.Commands.Issue.CONTRACT_RULE_INPUTS,
                InvoiceContract.Commands.Issue.CONTRACT_RULE_OUTPUTS,
                InvoiceContract.Commands.Issue.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue {
                actualValidationMessages.contains(it)
            }
        }
    }

    @Test
    fun `Amend command produces the expected validation responses`() {
        val contractCommand = InvoiceContract.Commands.Amend()
        val expectedValidationMessages = listOf(
                InvoiceContract.Commands.Amend.CONTRACT_RULE_INPUTS,
                InvoiceContract.Commands.Amend.CONTRACT_RULE_OUTPUTS,
                InvoiceContract.Commands.Amend.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue {
                actualValidationMessages.contains(it)
            }
        }
    }

    @Test
    fun `ChangeOwner command produces the expected validation responses`() {
        val contractCommand = InvoiceContract.Commands.ChangeOwner()
        val expectedValidationMessages = listOf(
                InvoiceContract.Commands.ChangeOwner.CONTRACT_RULE_INPUTS,
                InvoiceContract.Commands.ChangeOwner.CONTRACT_RULE_OUTPUTS,
                InvoiceContract.Commands.ChangeOwner.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue {
                actualValidationMessages.contains(it)
            }
        }
    }

    @Test
    fun `Cancel command produces the expected validation responses`() {
        val contractCommand = InvoiceContract.Commands.Cancel()
        val expectedValidationMessages = listOf(
                InvoiceContract.Commands.Cancel.CONTRACT_RULE_INPUTS,
                InvoiceContract.Commands.Cancel.CONTRACT_RULE_OUTPUTS,
                InvoiceContract.Commands.Cancel.CONTRACT_RULE_SIGNERS
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
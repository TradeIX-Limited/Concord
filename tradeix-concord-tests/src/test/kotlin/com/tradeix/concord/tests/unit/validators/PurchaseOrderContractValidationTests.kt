package com.tradeix.concord.tests.unit.validators

import com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PurchaseOrderContractValidationTests {

    @Test
    fun `Issue command produces the expected validation responses`() {
        val contractCommand = PurchaseOrderContract.Commands.Issue()
        val expectedValidationMessages = listOf(
                PurchaseOrderContract.Commands.Issue.CONTRACT_RULE_INPUTS,
                PurchaseOrderContract.Commands.Issue.CONTRACT_RULE_OUTPUTS,
                PurchaseOrderContract.Commands.Issue.CONTRACT_RULE_ENTITIES,
                PurchaseOrderContract.Commands.Issue.CONTRACT_RULE_OWNER,
                PurchaseOrderContract.Commands.Issue.CONTRACT_RULE_SIGNERS
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
        val contractCommand = PurchaseOrderContract.Commands.Amend()
        val expectedValidationMessages = listOf(
                PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_INPUTS,
                PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_OUTPUTS,
                PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_ENTITIES,
                PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_OWNER,
                PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_SIGNERS
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
        val contractCommand = PurchaseOrderContract.Commands.ChangeOwner()
        val expectedValidationMessages = listOf(
                PurchaseOrderContract.Commands.ChangeOwner.CONTRACT_RULE_INPUTS,
                PurchaseOrderContract.Commands.ChangeOwner.CONTRACT_RULE_OUTPUTS,
                PurchaseOrderContract.Commands.ChangeOwner.CONTRACT_RULE_OWNER,
                PurchaseOrderContract.Commands.ChangeOwner.CONTRACT_RULE_SIGNERS
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
        val contractCommand = PurchaseOrderContract.Commands.Cancel()
        val expectedValidationMessages = listOf(
                PurchaseOrderContract.Commands.Cancel.CONTRACT_RULE_INPUTS,
                PurchaseOrderContract.Commands.Cancel.CONTRACT_RULE_OUTPUTS,
                PurchaseOrderContract.Commands.Cancel.CONTRACT_RULE_OWNER,
                PurchaseOrderContract.Commands.Cancel.CONTRACT_RULE_SIGNERS
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
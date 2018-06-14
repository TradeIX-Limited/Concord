package com.tradeix.concord.tests.unit.validators.contracts

import com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PurchaseOrderContractValidationTests {

    @Test
    fun `Issue command produces the expected validation responses`() {
        val contractCommand = PurchaseOrderContract.Issue()
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()
        val expectedValidationMessages = listOf(
                PurchaseOrderContract.Issue.CONTRACT_RULE_INPUTS,
                PurchaseOrderContract.Issue.CONTRACT_RULE_OUTPUTS,
                PurchaseOrderContract.Issue.CONTRACT_RULE_ENTITIES,
                PurchaseOrderContract.Issue.CONTRACT_RULE_OWNER,
                PurchaseOrderContract.Issue.CONTRACT_RULE_SIGNERS
        )

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }

    @Test
    fun `Amend command produces the expected validation responses`() {
        val contractCommand = PurchaseOrderContract.Amend()
        val expectedValidationMessages = listOf(
                PurchaseOrderContract.Amend.CONTRACT_RULE_INPUTS,
                PurchaseOrderContract.Amend.CONTRACT_RULE_OUTPUTS,
                PurchaseOrderContract.Amend.CONTRACT_RULE_ENTITIES,
                PurchaseOrderContract.Amend.CONTRACT_RULE_OWNER,
                PurchaseOrderContract.Amend.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }

    @Test
    fun `ChangeOwner command produces the expected validation responses`() {
        val contractCommand = PurchaseOrderContract.ChangeOwner()
        val expectedValidationMessages = listOf(
                PurchaseOrderContract.ChangeOwner.CONTRACT_RULE_INPUTS,
                PurchaseOrderContract.ChangeOwner.CONTRACT_RULE_OUTPUTS,
                PurchaseOrderContract.ChangeOwner.CONTRACT_RULE_OWNER,
                PurchaseOrderContract.ChangeOwner.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }

    @Test
    fun `Cancel command produces the expected validation responses`() {
        val contractCommand = PurchaseOrderContract.Cancel()
        val expectedValidationMessages = listOf(
                PurchaseOrderContract.Cancel.CONTRACT_RULE_INPUTS,
                PurchaseOrderContract.Cancel.CONTRACT_RULE_OUTPUTS,
                PurchaseOrderContract.Cancel.CONTRACT_RULE_OWNER,
                PurchaseOrderContract.Cancel.CONTRACT_RULE_SIGNERS
        )
        val actualValidationMessages: Iterable<String> = contractCommand.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue { actualValidationMessages.contains(it) }
        }
    }
}
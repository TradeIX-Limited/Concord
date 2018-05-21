package com.tradeix.concord.shared.domain.contracts

import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.validation.*
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

class InvoiceContract : Contract {

    companion object {
        @JvmStatic
        val INVOICE_CONTRACT_ID = "com.tradeix.concord.shared.domain.contracts.InvoiceContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands>()
        command.value.validate(tx, command.signers)
    }

    abstract class Commands : ContractValidator(), CommandData {

        class Issue : Commands() {

            companion object {
                const val CONTRACT_RULE_INPUTS =
                        "On invoice issuance, zero input states must be consumed."

                const val CONTRACT_RULE_OUTPUTS =
                        "On invoice issuance, only one output state must be created."

                const val CONTRACT_RULE_SIGNERS =
                        "On invoice issuance, all participants must sign the transaction."
            }

            override fun onValidationBuilding(validationBuilder: ContractValidationBuilder, signers: List<PublicKey>) {

                // Transaction Validation
                validationBuilder
                        .property(LedgerTransaction::inputs)
                        .isEmpty(CONTRACT_RULE_INPUTS)

                validationBuilder
                        .property(LedgerTransaction::outputs)
                        .hasSize(1, CONTRACT_RULE_OUTPUTS)

                // State Validation
                val outputState = validationBuilder
                        .getTransactionState { it.outputsOfType<InvoiceState>().single() }

                val outputStateValidationBuilder = validationBuilder
                        .validationBuilderFor { outputState }

                outputStateValidationBuilder
                        .property(InvoiceState::participants)
                        .map { it.owningKey }
                        .containsAll(signers, CONTRACT_RULE_SIGNERS)
            }
        }

        class Amend : Commands() {

            companion object {
                const val CONTRACT_RULE_INPUTS =
                        "On invoice amendment, only one input state must be consumed."

                const val CONTRACT_RULE_OUTPUTS =
                        "On invoice amendment, only one output state must be created."

                const val CONTRACT_RULE_SIGNERS =
                        "On invoice amendment, all participants must sign the transaction."
            }

            override fun onValidationBuilding(validationBuilder: ContractValidationBuilder, signers: List<PublicKey>) {

                // Transaction Validation
                validationBuilder
                        .property(LedgerTransaction::inputs)
                        .hasSize(1, CONTRACT_RULE_INPUTS)

                validationBuilder
                        .property(LedgerTransaction::outputs)
                        .hasSize(1, CONTRACT_RULE_OUTPUTS)

                // State Validation
                val outputState = validationBuilder
                        .getTransactionState { it.outputsOfType<InvoiceState>().single() }

                val outputStateValidationBuilder = validationBuilder
                        .validationBuilderFor { outputState }

                outputStateValidationBuilder
                        .property(InvoiceState::participants)
                        .map { it.owningKey }
                        .containsAll(signers, CONTRACT_RULE_SIGNERS)
            }
        }

        class ChangeOwner : Commands() {

            companion object {
                const val CONTRACT_RULE_INPUTS =
                        "On invoice ownership change, only one input state must be consumed."

                const val CONTRACT_RULE_OUTPUTS =
                        "On invoice ownership change, only one output state must be created."

                const val CONTRACT_RULE_SIGNERS =
                        "On invoice ownership change, all participants must sign the transaction."
            }

            override fun onValidationBuilding(validationBuilder: ContractValidationBuilder, signers: List<PublicKey>) {

                // Transaction Validation
                validationBuilder
                        .property(LedgerTransaction::inputs)
                        .hasSize(1, CONTRACT_RULE_INPUTS)

                validationBuilder
                        .property(LedgerTransaction::outputs)
                        .hasSize(1, CONTRACT_RULE_OUTPUTS)

                // State Validation
                val outputState = validationBuilder
                        .getTransactionState { it.outputsOfType<InvoiceState>().single() }

                val outputStateValidationBuilder = validationBuilder
                        .validationBuilderFor { outputState }

                outputStateValidationBuilder
                        .property(InvoiceState::participants)
                        .map { it.owningKey }
                        .containsAll(signers, CONTRACT_RULE_SIGNERS)
            }
        }

        class Cancel : Commands() {

            companion object {
                const val CONTRACT_RULE_INPUTS =
                        "On invoice cancellation, only one input state must be consumed."

                const val CONTRACT_RULE_OUTPUTS =
                        "On invoice cancellation, zero output states must be created."

                const val CONTRACT_RULE_SIGNERS =
                        "On invoice cancellation, all participants must sign the transaction."
            }

            override fun onValidationBuilding(validationBuilder: ContractValidationBuilder, signers: List<PublicKey>) {

                // Transaction Validation
                validationBuilder
                        .property(LedgerTransaction::inputs)
                        .hasSize(1, CONTRACT_RULE_INPUTS)

                validationBuilder
                        .property(LedgerTransaction::outputs)
                        .isEmpty(CONTRACT_RULE_OUTPUTS)

                // State Validation
                val inputState = validationBuilder
                        .getTransactionState { it.inputsOfType<InvoiceState>().single() }

                val inputStateValidationBuilder = validationBuilder
                        .validationBuilderFor { inputState }

                inputStateValidationBuilder
                        .property(InvoiceState::participants)
                        .map { it.owningKey }
                        .containsAll(signers, CONTRACT_RULE_SIGNERS)
            }
        }
    }
}
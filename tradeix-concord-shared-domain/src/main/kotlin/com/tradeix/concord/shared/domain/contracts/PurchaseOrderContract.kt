package com.tradeix.concord.shared.domain.contracts

import com.tradeix.concord.shared.domain.states.PurchaseOrderState
import com.tradeix.concord.shared.validation.*
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

class PurchaseOrderContract : Contract {

    companion object {
        @JvmStatic
        val PURCHASE_ORDER_CONTRACT_ID = "com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands>()
        command.value.validate(tx, command.signers)
    }

    abstract class Commands : ContractValidator(), CommandData {

        class Issue : Commands() {

            companion object {
                const val CONTRACT_RULE_INPUTS =
                        "On purchase order issuance, zero input states must be consumed."

                const val CONTRACT_RULE_OUTPUTS =
                        "On purchase order issuance, only one output state must be created."

                const val CONTRACT_RULE_ENTITIES =
                        "On purchase order issuance, the buyer and the supplier must not be the same entity."

                const val CONTRACT_RULE_OWNER =
                        "On purchase order issuance, the buyer must be the owner."

                const val CONTRACT_RULE_SIGNERS =
                        "On purchase order issuance, all participants must sign the transaction."
            }

            override fun onValidationBuilding(
                    validationBuilder: ValidationBuilder<LedgerTransaction>, signers: List<PublicKey>) {

                // Transaction Validation
                validationBuilder
                        .property(LedgerTransaction::inputs)
                        .isEmpty(CONTRACT_RULE_INPUTS)

                validationBuilder
                        .property(LedgerTransaction::outputs)
                        .hasSize(1, CONTRACT_RULE_OUTPUTS)

                // State Validation
                val outputState = validationBuilder
                        .select { it.outputsOfType<PurchaseOrderState>().single() }

                val outputStateValidationBuilder = validationBuilder
                        .validationBuilderFor { outputState }

                outputStateValidationBuilder
                        .property(PurchaseOrderState::buyer)
                        .isNotEqualTo(outputState?.supplier, CONTRACT_RULE_ENTITIES)

                outputStateValidationBuilder
                        .property(PurchaseOrderState::buyer)
                        .isEqualTo(outputState?.owner, CONTRACT_RULE_OWNER)

                outputStateValidationBuilder
                        .property(PurchaseOrderState::participants)
                        .map { it.owningKey }
                        .inverseContainsAll(signers, CONTRACT_RULE_SIGNERS)
            }
        }

        class Amend : Commands() {

            companion object {
                const val CONTRACT_RULE_INPUTS =
                        "On purchase order amendment, only one input state must be consumed."

                const val CONTRACT_RULE_OUTPUTS =
                        "On purchase order amendment, only one output state must be created."

                const val CONTRACT_RULE_ENTITIES =
                        "On purchase order amendment, the buyer and the supplier must not be the same entity."

                const val CONTRACT_RULE_OWNER =
                        "On purchase order amendment, the buyer must be the owner."

                const val CONTRACT_RULE_SIGNERS =
                        "On purchase order amendment, all participants must sign the transaction."
            }

            override fun onValidationBuilding(
                    validationBuilder: ValidationBuilder<LedgerTransaction>, signers: List<PublicKey>) {

                // Transaction Validation
                validationBuilder
                        .property(LedgerTransaction::inputs)
                        .hasSize(1, CONTRACT_RULE_INPUTS)

                validationBuilder
                        .property(LedgerTransaction::outputs)
                        .hasSize(1, CONTRACT_RULE_OUTPUTS)

                // State Validation
                val outputState = validationBuilder
                        .select { it.outputsOfType<PurchaseOrderState>().single() }

                val outputStateValidationBuilder = validationBuilder
                        .validationBuilderFor { outputState }

                outputStateValidationBuilder
                        .property(PurchaseOrderState::buyer)
                        .isNotEqualTo(outputState?.supplier, CONTRACT_RULE_ENTITIES)

                outputStateValidationBuilder
                        .property(PurchaseOrderState::buyer)
                        .isEqualTo(outputState?.owner, CONTRACT_RULE_OWNER)

                outputStateValidationBuilder
                        .property(PurchaseOrderState::participants)
                        .map { it.owningKey }
                        .inverseContainsAll(signers, CONTRACT_RULE_SIGNERS)
            }
        }

        class ChangeOwner : Commands() {

            companion object {
                const val CONTRACT_RULE_INPUTS =
                        "On purchase order ownership change, only one input state must be consumed."

                const val CONTRACT_RULE_OUTPUTS =
                        "On purchase order ownership change, only one output state must be created."

                const val CONTRACT_RULE_OWNER =
                        "On purchase order ownership change, the supplier must be the new owner."

                const val CONTRACT_RULE_SIGNERS =
                        "On purchase order ownership change, all participants must sign the transaction."
            }

            override fun onValidationBuilding(
                    validationBuilder: ValidationBuilder<LedgerTransaction>, signers: List<PublicKey>) {

                // Transaction Validation
                validationBuilder
                        .property(LedgerTransaction::inputs)
                        .hasSize(1, CONTRACT_RULE_INPUTS)

                validationBuilder
                        .property(LedgerTransaction::outputs)
                        .hasSize(1, CONTRACT_RULE_OUTPUTS)

                // State Validation
                val outputState = validationBuilder
                        .select { it.outputsOfType<PurchaseOrderState>().single() }

                val outputStateValidationBuilder = validationBuilder
                        .validationBuilderFor { outputState }

                outputStateValidationBuilder
                        .property(PurchaseOrderState::supplier)
                        .isEqualTo(outputState?.owner, CONTRACT_RULE_OWNER)

                outputStateValidationBuilder
                        .property(PurchaseOrderState::participants)
                        .map { it.owningKey }
                        .inverseContainsAll(signers, CONTRACT_RULE_SIGNERS)
            }
        }

        class Cancel : Commands() {

            companion object {
                const val CONTRACT_RULE_INPUTS =
                        "On purchase order cancellation, only one input state must be consumed."

                const val CONTRACT_RULE_OUTPUTS =
                        "On purchase order cancellation, zero output states must be created."

                const val CONTRACT_RULE_OWNER =
                        "On purchase order cancellation, the buyer must be the owner."

                const val CONTRACT_RULE_SIGNERS =
                        "On purchase order cancellation, all participants must sign the transaction."
            }

            override fun onValidationBuilding(
                    validationBuilder: ValidationBuilder<LedgerTransaction>, signers: List<PublicKey>) {

                // Transaction Validation
                validationBuilder
                        .property(LedgerTransaction::inputs)
                        .hasSize(1, CONTRACT_RULE_INPUTS)

                validationBuilder
                        .property(LedgerTransaction::outputs)
                        .isEmpty(CONTRACT_RULE_OUTPUTS)

                // State Validation
                val inputState = validationBuilder
                        .select { it.inputsOfType<PurchaseOrderState>().single() }

                val inputStateValidationBuilder = validationBuilder
                        .validationBuilderFor { inputState }

                inputStateValidationBuilder
                        .property(PurchaseOrderState::buyer)
                        .isEqualTo(inputState?.owner, CONTRACT_RULE_OWNER)

                inputStateValidationBuilder
                        .property(PurchaseOrderState::participants)
                        .map { it.owningKey }
                        .inverseContainsAll(signers, CONTRACT_RULE_SIGNERS)
            }
        }
    }
}
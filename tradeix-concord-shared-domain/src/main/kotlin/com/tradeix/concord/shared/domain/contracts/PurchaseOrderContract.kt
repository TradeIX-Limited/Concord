package com.tradeix.concord.shared.domain.contracts

import com.tradeix.concord.shared.domain.states.PurchaseOrderState
import com.tradeix.concord.shared.extensions.toOwningKeys
import com.tradeix.concord.shared.validation.ContractValidationBuilder
import com.tradeix.concord.shared.validation.ValidatedCommand
import com.tradeix.concord.shared.validation.extensions.hasSize
import com.tradeix.concord.shared.validation.extensions.isEmpty
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction

class PurchaseOrderContract : Contract {

    companion object {
        @JvmStatic
        val PURCHASE_ORDER_CONTRACT_ID = "com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<ValidatedCommand>()
        command.value.validate(tx, command.signers)
    }

    class Issue : ValidatedCommand() {

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

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.isEmpty(CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.hasSize(1, CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_ENTITIES, {
                val outputState = it
                        .outputsOfType<PurchaseOrderState>()
                        .single()

                outputState.buyer != outputState.supplier
            })

            validationBuilder.validateWith(CONTRACT_RULE_OWNER, {
                val outputState = it
                        .outputsOfType<PurchaseOrderState>()
                        .single()

                outputState.buyer == outputState.owner
            })

            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<PurchaseOrderState>()
                        .single()
                        .participants
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }

    class Amend : ValidatedCommand() {

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

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.hasSize(1, CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.hasSize(1, CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_ENTITIES, {
                val outputState = it
                        .outputsOfType<PurchaseOrderState>()
                        .single()

                outputState.buyer != outputState.supplier
            })

            validationBuilder.validateWith(CONTRACT_RULE_OWNER, {
                val outputState = it
                        .outputsOfType<PurchaseOrderState>()
                        .single()

                outputState.buyer == outputState.owner
            })

            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<PurchaseOrderState>()
                        .single()
                        .participants
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }

    class ChangeOwner : ValidatedCommand() {

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

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.hasSize(1, CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.hasSize(1, CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_OWNER, {
                val outputState = it
                        .outputsOfType<PurchaseOrderState>()
                        .single()

                outputState.supplier == outputState.owner
            })

            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<PurchaseOrderState>()
                        .single()
                        .participants
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }

    class Cancel : ValidatedCommand() {

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

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.hasSize(1, CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.isEmpty(CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_OWNER, {
                val outputState = it
                        .inputsOfType<PurchaseOrderState>()
                        .single()

                outputState.buyer == outputState.owner
            })

            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .inputsOfType<PurchaseOrderState>()
                        .single()
                        .participants
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }
}
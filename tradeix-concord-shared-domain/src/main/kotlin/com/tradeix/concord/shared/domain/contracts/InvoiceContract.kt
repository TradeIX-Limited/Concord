package com.tradeix.concord.shared.domain.contracts

import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.toOwningKeys
import com.tradeix.concord.shared.validation.ContractValidationBuilder
import com.tradeix.concord.shared.validation.ValidatedCommand
import com.tradeix.concord.shared.validation.extensions.isEmpty
import com.tradeix.concord.shared.validation.extensions.isNotEmpty
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction

class InvoiceContract : Contract {

    companion object {
        @JvmStatic
        val INVOICE_CONTRACT_ID = InvoiceContract::class.qualifiedName!!
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<ValidatedCommand>()
        command.value.validate(tx, command.signers)
    }

    class Issue : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On invoice issuance, zero input states must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On invoice issuance, at least one output state must be created."

            const val CONTRACT_RULE_SIGNERS =
                    "On invoice issuance, all participants must sign the transaction."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            //Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.isEmpty(CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.isNotEmpty(CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<InvoiceState>()
                        .flatMap { it.participants }
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }

    class Amend : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On invoice amendment, at least one input state must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On invoice amendment, at least one output state must be created."

            const val CONTRACT_RULE_INPUTS_OUTPUTS =
                    "On invoice amendment, the number of inputs and outputs must be equal."

            const val CONTRACT_RULE_SIGNERS =
                    "On invoice amendment, all participants must sign the transaction."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.isNotEmpty(CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.isNotEmpty(CONTRACT_RULE_OUTPUTS)
            })

            validationBuilder.validateWith(CONTRACT_RULE_INPUTS_OUTPUTS, {
                it.inputs.size == it.outputs.size
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<InvoiceState>()
                        .flatMap { it.participants }
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }

    class ChangeOwner : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On invoice ownership change, at least one input state must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On invoice ownership change, at least one output state must be created."

            const val CONTRACT_RULE_INPUTS_OUTPUTS =
                    "On invoice ownership change, the number of inputs and outputs must be equal."

            const val CONTRACT_RULE_SIGNERS =
                    "On invoice ownership change, all participants must sign the transaction."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.isNotEmpty(CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.isNotEmpty(CONTRACT_RULE_OUTPUTS)
            })

            validationBuilder.validateWith(CONTRACT_RULE_INPUTS_OUTPUTS, {
                it.inputs.size == it.outputs.size
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<InvoiceState>()
                        .flatMap { it.participants }
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }

    class Cancel : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On invoice cancellation, at least one input state must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On invoice cancellation, zero output states must be created."

            const val CONTRACT_RULE_SIGNERS =
                    "On invoice cancellation, all participants must sign the transaction."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.isNotEmpty(CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.isEmpty(CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .inputsOfType<InvoiceState>()
                        .flatMap { it.participants }
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }
}
package com.tradeix.concord.shared.domain.contracts

import com.tradeix.concord.shared.domain.states.InvoiceEligibilityState
import com.tradeix.concord.shared.extensions.toOwningKeys
import com.tradeix.concord.shared.validation.ContractValidationBuilder
import com.tradeix.concord.shared.validation.ValidatedCommand
import com.tradeix.concord.shared.validation.extensions.isEmpty
import com.tradeix.concord.shared.validation.extensions.isNotEmpty
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction

class InvoiceEligibilityContract : Contract {

    companion object {
        @JvmStatic
        val INVOICE_ELIGIBILITY_CONTRACT_ID = InvoiceEligibilityContract::class.qualifiedName!!
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<ValidatedCommand>()
        command.value.validate(tx, command.signers)
    }

    class Issue : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On invoice eligibility issuance, zero input states must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On invoice eligibility issuance, at least one output state must be created."

            const val CONTRACT_RULE_SIGNERS =
                    "On invoice eligibility issuance, all participants must sign the transaction."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.isEmpty(CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.isNotEmpty(CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<InvoiceEligibilityState>()
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
                    "On invoice eligibility amendment, at least one input state must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On invoice eligibility amendment, at least one output state must be created."

            const val CONTRACT_RULE_INPUTS_OUTPUTS =
                    "On invoice eligibility amendment, the number of inputs and outputs must be equal."

            const val CONTRACT_RULE_SIGNERS =
                    "On invoice eligibility amendment, all participants must sign the transaction."
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
                        .outputsOfType<InvoiceEligibilityState>()
                        .flatMap { it.participants }
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }
}
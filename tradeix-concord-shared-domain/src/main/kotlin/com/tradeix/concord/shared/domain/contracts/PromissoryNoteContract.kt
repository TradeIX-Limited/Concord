package com.tradeix.concord.shared.domain.contracts

import com.tradeix.concord.shared.domain.states.PromissoryNoteState
import com.tradeix.concord.shared.extensions.toOwningKeys
import com.tradeix.concord.shared.validation.ContractValidationBuilder
import com.tradeix.concord.shared.validation.ValidatedCommand
import com.tradeix.concord.shared.validation.extensions.hasSize
import com.tradeix.concord.shared.validation.extensions.isEmpty
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction

class PromissoryNoteContract : Contract {

    companion object {
        @JvmStatic
        val PROMISSORY_NOTE_CONTRACT_ID = "com.tradeix.concord.shared.domain.contracts.PromissoryNoteContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<ValidatedCommand>()
        command.value.validate(tx, command.signers)
    }

    class Issue : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On promissory note issuance, zero input states must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On promissory note issuance, only one output state must be created."

            const val CONTRACT_RULE_ENTITIES =
                    "On promissory note issuance, the obligor and the obligee cannot be the same entity."

            const val CONTRACT_RULE_SIGNERS =
                    "On promissory note issuance, all participants are required to sign the transaction."
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
                        .outputsOfType<PromissoryNoteState>()
                        .single()

                outputState.obligee != outputState.obligor
            })

            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<PromissoryNoteState>()
                        .single()
                        .participants
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }
}
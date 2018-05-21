package com.tradeix.concord.shared.domain.contracts

import com.tradeix.concord.shared.domain.states.PromissoryNoteState
import com.tradeix.concord.shared.validation.*
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

class PromissoryNoteContract : Contract {

    companion object {
        @JvmStatic
        val PROMISSORY_NOTE_CONTRACT_ID = "com.tradeix.concord.shared.domain.contracts.PromissoryNoteContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands>()
        command.value.validate(tx, command.signers)
    }

    abstract class Commands : ContractValidator(), CommandData {

        class Issue : Commands() {

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
                        .getTransactionState { it.outputsOfType<PromissoryNoteState>().single() }

                val outputStateValidationBuilder = validationBuilder
                        .validationBuilderFor { outputState }

                outputStateValidationBuilder
                        .property(PromissoryNoteState::obligee)
                        .isNotEqualTo(outputState?.obligor, CONTRACT_RULE_ENTITIES)

                outputStateValidationBuilder
                        .property(PromissoryNoteState::participants)
                        .map { it.owningKey }
                        .containsAll(signers, CONTRACT_RULE_SIGNERS)
            }
        }
    }
}
package com.tradeix.concord.contracts

import com.tradeix.concord.states.PurchaseOrderState
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.Requirements.using
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.contracts.requireThat
import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

class PurchaseOrderContract : Contract {

    companion object {
        @JvmStatic
        val PURCHASE_ORDER_CONTRACT_ID = "com.tradeix.concord.contracts.PurchaseOrderContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands>()
        command.value.verify(tx, command.signers)
    }

    interface Commands : CommandData {

        fun verify(tx: LedgerTransaction, signers: List<PublicKey>)

        class Issue : Commands {

            companion object {
                val CONTRACT_RULE_INPUTS =
                        "Zero inputs should be consumed when issuing a purchase order."

                val CONTRACT_RULE_OUTPUTS =
                        "Only one output state should be created when issuing a purchase order."

                val CONTRACT_RULE_ENTITIES =
                        "The buyer and the supplier cannot be the same entity when issuing a purchase order."

                val CONTRACT_RULE_OWNER =
                        "The buyer must be the owner when issuing a purchase order."

                val CONTRACT_RULE_SIGNERS =
                        "All participants are required to sign when issuing a purchase order."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.isEmpty())
                CONTRACT_RULE_OUTPUTS using (tx.outputs.size == 1)

                // State Rules
                val outputState = tx.outputsOfType<PurchaseOrderState>().single()
                CONTRACT_RULE_ENTITIES using (outputState.buyer != outputState.supplier)
                CONTRACT_RULE_OWNER using (outputState.buyer == outputState.owner)
                CONTRACT_RULE_SIGNERS using (signers.containsAll(outputState.participants.map { it.owningKey }))
            }
        }

        class Amend : Commands {

            companion object {
                val CONTRACT_RULE_INPUTS =
                        "Only one input should be consumed when amending a purchase order."

                val CONTRACT_RULE_OUTPUTS =
                        "Only one output state should be created when amending a purchase order."

                val CONTRACT_RULE_ENTITIES =
                        "The buyer and the supplier cannot be the same entity when amending a purchase order."

                val CONTRACT_RULE_OWNER =
                        "The buyer must be the owner when amending a purchase order."

                val CONTRACT_RULE_SIGNERS =
                        "All participants are required to sign when amending a purchase order."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.size == 1)
                CONTRACT_RULE_OUTPUTS using (tx.outputs.size == 1)

                // State Rules
                val outputState = tx.outputsOfType<PurchaseOrderState>().single()
                CONTRACT_RULE_ENTITIES using (outputState.buyer != outputState.supplier)
                CONTRACT_RULE_OWNER using (outputState.buyer == outputState.owner)
                CONTRACT_RULE_SIGNERS using (signers.containsAll(outputState.participants.map { it.owningKey }))
            }
        }

        class ChangeOwner : Commands {

            companion object {
                val CONTRACT_RULE_INPUTS =
                        "One or more inputs should be consumed when changing ownership of a purchase order."

                val CONTRACT_RULE_OUTPUTS =
                        "The number of outputs must be equal to the number of inputs when changing ownership of a purchase order."

                val CONTRACT_RULE_OWNER =
                        "The supplier must be the new owner when changing ownership of a purchase order."

                val CONTRACT_RULE_SIGNERS =
                        "All participants are required to sign when changing ownership of a purchase order."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.isNotEmpty())
                CONTRACT_RULE_OUTPUTS using (tx.outputs.size == tx.inputs.size)

                // State Rules
                tx.outputsOfType<PurchaseOrderState>().forEach {
                    CONTRACT_RULE_OWNER using (it.owner != it.buyer)
                    CONTRACT_RULE_SIGNERS using (signers.containsAll(it.participants.map { it.owningKey }))
                }
            }
        }

        class Cancel : Commands {

            companion object {
                val CONTRACT_RULE_INPUTS =
                        "Only one input should be consumed when cancelling a purchase order."

                val CONTRACT_RULE_OUTPUTS =
                        "Zero output states should be created when cancelling a purchase order."

                val CONTRACT_RULE_OWNER =
                        "The buyer must be the owner when cancelling a purchase order."

                val CONTRACT_RULE_SIGNERS =
                        "All participants are required to sign when cancelling a purchase order."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.size == 1)
                CONTRACT_RULE_OUTPUTS using (tx.outputs.isEmpty())

                // State Rules
                val inputState = tx.inputsOfType<PurchaseOrderState>().single()
                CONTRACT_RULE_OWNER using (inputState.owner == inputState.buyer)
                CONTRACT_RULE_SIGNERS using (signers.containsAll(inputState.participants.map { it.owningKey }))
            }
        }
    }
}
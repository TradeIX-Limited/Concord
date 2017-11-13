package com.tradeix.concord.contracts

import com.tradeix.concord.states.TradeAssetState
import net.corda.core.contracts.*
import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

open class TradeAssetContract : Contract {

    companion object {
        @JvmStatic
        val TRADE_ASSET_CONTRACT_ID = "com.tradeix.concord.contracts.TradeAssetContract"
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
                        "On issuance, zero input states should be consumed."

                val CONTRACT_RULE_OUTPUTS =
                        "On issuance, only one output state should be created."

                val CONTRACT_RULE_ENTITIES =
                        "On issuance, the buyer and the supplier cannot be the same entity."

                val CONTRACT_RULE_SIGNERS =
                        "On issuance, all participants must sign the transaction."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.isEmpty())
                CONTRACT_RULE_OUTPUTS using (tx.outputs.size == 1)

                // State Rules
                val outputState = tx.outputsOfType<TradeAssetState>().single()
                CONTRACT_RULE_ENTITIES using (outputState.buyer != outputState.supplier)
                CONTRACT_RULE_SIGNERS using (signers.containsAll(outputState.participants.map { it.owningKey }))
            }
        }

        class ChangeOwner : Commands {
            companion object {
                val CONTRACT_RULE_INPUTS =
                        "On ownership change, at least one input should be consumed."

                val CONTRACT_RULE_OUTPUTS =
                        "On ownership change, the number of output and input states should be equal."

                val CONTRACT_RULE_ENTITIES =
                        "On ownership change, the supplier and the new owner cannot be the same entity."

                val CONTRACT_RULE_SIGNERS =
                        "On ownership change, all participants must sign the transaction."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.isNotEmpty())
                CONTRACT_RULE_OUTPUTS using (tx.outputs.size == tx.inputs.size)

                // State Rules
                tx.outputsOfType<TradeAssetState>().forEach {
                    CONTRACT_RULE_ENTITIES using (it.owner != it.supplier)
                    CONTRACT_RULE_SIGNERS using (signers.containsAll(it.participants.map { it.owningKey }))
                }
            }
        }

        class Cancel : Commands {
            companion object {
                val CONTRACT_RULE_INPUTS =
                        "On cancellation, only one input should be consumed."

                val CONTRACT_RULE_OUTPUTS =
                        "On cancellation, zero output states should be created."

                val CONTRACT_RULE_CANCEL =
                        "On cancellation, nobody can cancel unless the owner is the supplier."

                val CONTRACT_RULE_SIGNERS =
                        "On cancellation, all participants must sign the transaction."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.size == 1)
                CONTRACT_RULE_OUTPUTS using (tx.outputs.isEmpty())

                // State Rules
                val inputState = tx.inputsOfType<TradeAssetState>().single()
                CONTRACT_RULE_CANCEL using (inputState.owner == inputState.supplier)
                CONTRACT_RULE_SIGNERS using (signers.containsAll(inputState.participants.map { it.owningKey }))
            }
        }

        class Amend : Commands {
            companion object {
                val CONTRACT_RULE_INPUTS =
                        "On amendment, only one input should be consumed."

                val CONTRACT_RULE_OUTPUTS =
                        "On amendment, only one output state should be created."

                val CONTRACT_RULE_AMEND =
                        "On amendment, the supplier must be the owner when amending a trade asset."

                val CONTRACT_RULE_SIGNERS =
                        "On amendment, all participants must sign the transaction."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.size == 1)
                CONTRACT_RULE_OUTPUTS using (tx.outputs.size == 1)

                // State Rules
                val outputState = tx.outputsOfType<TradeAssetState>().single()
                CONTRACT_RULE_AMEND using (outputState.supplier == outputState.owner)
                CONTRACT_RULE_SIGNERS using (signers.containsAll(outputState.participants.map { it.owningKey }))
            }
        }
    }
}

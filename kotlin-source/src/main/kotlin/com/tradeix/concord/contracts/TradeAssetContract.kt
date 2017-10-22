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
            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {

                "Zero input states should be consumed when issuing a trade asset." using
                        (tx.inputs.isEmpty())

                "Only one output state should be created when issuing a trade asset." using
                        (tx.outputs.size == 1)

                val outputState = tx.outputsOfType<TradeAssetState>().single()

                "The buyer and the supplier cannot be the same entity." using
                        (outputState.buyer != outputState.supplier)

                "All participants must sign the transaction." using
                        (signers.containsAll(outputState.participants.map { it.owningKey }))
            }
        }

        class ChangeOwner : Commands {
            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {

                "Only one input should be consumed when changing ownership of a trade asset." using
                        (tx.inputs.size == 1)

                "Only one output state should be created when changing ownership of a trade asset." using
                        (tx.outputs.size == 1)

                val outputState = tx.outputsOfType<TradeAssetState>().single()

                "The supplier and the new owner cannot be the same entity." using
                        (outputState.owner != outputState.supplier)

                "All participants must sign the transaction." using
                        (signers.containsAll(outputState.participants.map { it.owningKey }))
            }
        }

        class Cancel : Commands {
            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {

                "Only one input should be consumed when cancelling a trade asset." using
                        (tx.inputs.size == 1)

                "Zero output states should be created when cancelling a trade asset." using
                        (tx.outputs.isEmpty())

                val inputState = tx.inputsOfType<TradeAssetState>().single()

                "Nobody can cancel unless the owner is the supplier." using
                        (inputState.owner == inputState.supplier)

                "All participants must sign the transaction." using
                        (signers.containsAll(inputState.participants.map { it.owningKey }))
            }
        }

        class Amend : Commands {
            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {

                "Only one input should be consumed when amending a trade asset." using
                        (tx.inputs.size == 1)

                "Only one output state should be created when amending a trade asset." using
                        (tx.outputs.size == 1)

                val outputState = tx.outputsOfType<TradeAssetState>().single()

                "The supplier must be the owner when amending a trade asset." using
                        (outputState.supplier == outputState.owner)

                "All participants must sign the transaction." using
                        (signers.containsAll(outputState.participants.map { it.owningKey }))
            }
        }
    }
}

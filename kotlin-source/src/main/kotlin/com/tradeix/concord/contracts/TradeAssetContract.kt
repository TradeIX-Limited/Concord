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

                "No inputs should be consumed when issuing a purchase order." using
                        (tx.inputs.isEmpty())

                "Only one output state should be created." using
                        (tx.outputs.size == 1)

                val out = tx.outputsOfType<TradeAssetState>().single()

                "All of the participants must be signers." using
                        (signers.containsAll(out.participants.map { it.owningKey }))

                "The buyer and the supplier cannot be the same entity." using
                        (out.buyer != out.supplier)

            }
        }

        class ChangeOwner : Commands {
            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
                "Only one input should be consumed when changing owner on a purchase order." using
                        (tx.inputs.size == 1)

                "Only one output state should be created." using
                        (tx.outputs.size == 1)

                val out = tx.outputsOfType<TradeAssetState>().single()

                "All of the participants must be signers." using
                        (signers.containsAll(out.participants.map { it.owningKey }))

                "The supplier and the new owner cannot be the same entity." using
                        (out.owner != out.supplier)
            }
        }

        class Settle : Commands {
            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
            }
        }

        class Cancel : Commands {
            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
                "One Input should be present to cancel." using
                        (tx.inputs.size == 1)
                "There should not be any Output for cancellation." using
                        (tx.outputs.isEmpty())
                val tradeAssetState = tx.inputs.single().state.data as TradeAssetState
                "Funder cannot cancel the contract" using
                        (tradeAssetState.owner != tradeAssetState.supplier) //TBD
            }
        }
    }
}

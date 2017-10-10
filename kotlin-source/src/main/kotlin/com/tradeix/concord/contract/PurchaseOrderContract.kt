package com.tradeix.concord.contract

import com.tradeix.concord.state.PurchaseOrderState
import net.corda.core.contracts.*
import net.corda.core.transactions.LedgerTransaction
import net.corda.finance.POUNDS
import java.security.PublicKey

open class PurchaseOrderContract : Contract {

    companion object {
        @JvmStatic
        val PURCHASE_ORDER_CONTRACT_ID = "com.tradeix.concord.contract.PurchaseOrderContract"
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

                val out = tx.outputsOfType<PurchaseOrderState>().single()

                "All of the participants must be signers." using
                        (signers.containsAll(out.participants.map { it.owningKey }))

                "The buyer and the supplier cannot be the same entity." using
                        (out.buyer != out.supplier)

            }
        }

        class ChangeOwner : Commands {
            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
            }
        }

        class Settle : Commands {
            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) = requireThat {
            }
        }

    }
}

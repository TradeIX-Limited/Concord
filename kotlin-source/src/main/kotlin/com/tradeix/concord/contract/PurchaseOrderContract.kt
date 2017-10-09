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

                val out = tx.outputsOfType<PurchaseOrderState>().single()

                "No inputs should be consumed when issuing an IOU." using
                        (tx.inputs.isEmpty())

                "Only one output state should be created." using
                        (tx.outputs.size == 1)

                "The lender and the borrower cannot be the same entity." using
                        (out.buyer != out.supplier)

                "All of the participants must be signers." using
                        (signers.containsAll(out.participants.map { it.owningKey }))

                "The IOU's value must be non-negative." using
                        (out.purchaseOrder.amount > 0.POUNDS)
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

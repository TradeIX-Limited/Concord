package com.tradeix.concord.contracts

import com.tradeix.concord.states.InvoiceState
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.Requirements.using
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

class InvoiceContract : Contract {

    companion object {
        @JvmStatic
        val INVOICE_CONTRACT_ID = "com.tradeix.concord.contracts.InvoiceContract"
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands>()
        command.value.verify(tx, command.signers)
    }

    interface Commands : CommandData {
        fun verify(tx: LedgerTransaction, signers: List<PublicKey>)

        class Issue : Commands {
            companion object {
                val CONTRACT_RULE_INPUTS = "Zero inputs should be consumed when issuing an invoice."
                val CONTRACT_RULE_OUTPUTS = "Only one output should be created when issuing an invoice."
                val CONTRACT_RULE_SIGNERS = "All participants are required to sign when issuing an invoice."
                val CONTRACT_RULE_CONDUCTOR = "Supplier, Buyer and Funder participants are not allowed conduct transactions."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.isEmpty())
                CONTRACT_RULE_OUTPUTS using (tx.outputs.size == 1)

                // State Rules
                val outputState = tx.outputsOfType<InvoiceState>().single()
                CONTRACT_RULE_SIGNERS using (signers.containsAll(outputState.participants.map { it.owningKey }))

                CONTRACT_RULE_CONDUCTOR using (!(listOfNotNull(outputState.buyer, outputState.supplier, outputState.funder)).contains(outputState.conductor))
            }
        }

        class ChangeOwner : Commands {
            companion object {
                val CONTRACT_RULE_INPUTS = "At least one input should be consumed when changing ownership of an invoice."
                val CONTRACT_RULE_OUTPUTS = "At least one output should be created when changing ownership of an invoice."
                val CONTRACT_RULE_SIGNERS = "All participants are required to sign when changing ownership of an invoice."
                val CONTRACT_RULE_CONDUCTOR = "Supplier, Buyer and Funder participants are not allowed conduct transactions."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.isNotEmpty())
                CONTRACT_RULE_OUTPUTS using (tx.outputs.isNotEmpty())

                // State Rules
                val keys = tx.outputsOfType<InvoiceState>()
                        .flatMap { it.participants }
                        .map { it.owningKey }
                        .distinct()

                CONTRACT_RULE_SIGNERS using signers.containsAll(keys)

                tx.outputsOfType<InvoiceState>().forEach {
                    CONTRACT_RULE_CONDUCTOR using (!(listOfNotNull(it.buyer, it.supplier, it.funder)).contains(it.conductor))
                }
            }
        }

        class Amend : Commands {
            companion object {
                val CONTRACT_RULE_INPUTS = "Only one input should be consumed when amending an invoice."
                val CONTRACT_RULE_OUTPUTS = "Only one output should be created when amending an invoice."
                val CONTRACT_RULE_CONDUCTOR = "Supplier, Buyer and Funder participants are not allowed conduct transactions."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.size == 1)
                CONTRACT_RULE_OUTPUTS using (tx.outputs.size == 1)

                tx.outputsOfType<InvoiceState>().forEach {
                    CONTRACT_RULE_CONDUCTOR using (!(listOfNotNull(it.buyer, it.supplier, it.funder)).contains(it.conductor))
                }
            }
        }

        class Cancel : Commands {
            companion object {
                val CONTRACT_RULE_INPUTS = "Only one input should be consumed when cancelling an invoice."
                val CONTRACT_RULE_OUTPUTS = "Zero outputs should be created when cancelling an invoice."
                val CONTRACT_RULE_CONDUCTOR = "Supplier, Buyer and Funder participants are not allowed conduct transactions."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.size == 1)
                CONTRACT_RULE_OUTPUTS using (tx.outputs.isEmpty())

                tx.outputsOfType<InvoiceState>().forEach {
                    CONTRACT_RULE_CONDUCTOR using (!(listOfNotNull(it.buyer, it.supplier, it.funder)).contains(it.conductor))
                }
            }
        }

        class IPU : Commands {
            companion object {
                val CONTRACT_RULE_INPUTS = "Only one input should be consumed when setting an invoice to IPU."
                val CONTRACT_RULE_OUTPUTS = "Zero outputs should be created when setting an invoice to IPU."
                val CONTRACT_RULE_STATUS = "The invoice status must be set to IPU."
                val CONTRACT_RULE_CONDUCTOR = "Supplier, Buyer and Funder participants are not allowed conduct transactions."
            }

            override fun verify(tx: LedgerTransaction, signers: List<PublicKey>) {
                // Transaction Rules
                CONTRACT_RULE_INPUTS using (tx.inputs.size == 1)
                CONTRACT_RULE_OUTPUTS using (tx.outputs.size == 1)

                // State Rules
                CONTRACT_RULE_STATUS using (tx.outputsOfType<InvoiceState>().single().status == "IPU")

                tx.outputsOfType<InvoiceState>().forEach {
                    CONTRACT_RULE_CONDUCTOR using (!(listOfNotNull(it.buyer, it.supplier, it.funder)).contains(it.conductor))
                }
            }
        }
    }
}
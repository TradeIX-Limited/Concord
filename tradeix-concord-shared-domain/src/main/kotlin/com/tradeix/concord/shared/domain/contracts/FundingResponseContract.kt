package com.tradeix.concord.shared.domain.contracts

import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.extensions.toOwningKeys
import com.tradeix.concord.shared.validation.ContractValidationBuilder
import com.tradeix.concord.shared.validation.ValidatedCommand
import com.tradeix.concord.shared.validation.extensions.hasSize
import com.tradeix.concord.shared.validation.extensions.isEmpty
import com.tradeix.concord.shared.validation.extensions.isNotEmpty
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction

class FundingResponseContract : Contract {

    companion object {
        @JvmStatic
        val FundingResponse_CONTRACT_ID = FundingResponseContract::class.qualifiedName!!
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<ValidatedCommand>()
        command.value.validate(tx, command.signers)
    }

    class Issue : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On FundingResponse issuance, zero input states must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On Funding Response issuance, at least one output state must be created."

            const val CONTRACT_RULE_SIGNERS =
                    "On Funding Response issuance, all participants must sign the transaction."
            const val CONTRACT_RULE_STATUS =
                    "On Funding Response issuance, status has to be pending."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            //Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.isEmpty(CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.isNotEmpty(CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<FundingResponseState>()
                        .flatMap { it.participants }
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })

            //TO DO : Status has to be Pending

        }
    }

    class Accept : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On Funding Response Acceptance, only one input state must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On Funding Response Acceptance, only one output state must be created."

            const val CONTRACT_RULE_INPUTS_OUTPUTS =
                    "On Funding Response Acceptance, the number of inputs and outputs must be equal."

            const val CONTRACT_RULE_SIGNERS =
                    "On Funding Response Acceptance, all participants must sign the transaction."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.hasSize(1, CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.hasSize(1, CONTRACT_RULE_OUTPUTS)
            })

            validationBuilder.validateWith(CONTRACT_RULE_INPUTS_OUTPUTS, {
                it.inputs.size == it.outputs.size
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<FundingResponseState>()
                        .single()
                        .participants
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })

            //TO DO : Status has to be Accepted
        }
    }

    class Reject : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On Funding Response rejection, only one input state must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On Funding Response rejection, zero output states must be created."

            const val CONTRACT_RULE_SIGNERS =
                    "On Funding Response rejection, all participants must sign the transaction."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.hasSize(1, CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.isEmpty(CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .inputsOfType<FundingResponseState>()
                        .single()
                        .participants
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })

            //TO DO : Status has to be Rejected
        }
    }

}
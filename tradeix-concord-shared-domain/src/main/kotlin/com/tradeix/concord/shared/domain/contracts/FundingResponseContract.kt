package com.tradeix.concord.shared.domain.contracts

import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.extensions.isNotEmpty
import com.tradeix.concord.shared.extensions.toOwningKeys
import com.tradeix.concord.shared.validation.ContractValidationBuilder
import com.tradeix.concord.shared.validation.ValidatedCommand
import com.tradeix.concord.shared.validation.extensions.hasSize
import com.tradeix.concord.shared.validation.extensions.isEmpty
import net.corda.core.contracts.Contract
import net.corda.core.contracts.requireSingleCommand
import net.corda.core.transactions.LedgerTransaction

class FundingResponseContract : Contract {

    companion object {
        @JvmStatic
        val FUNDING_RESPONSE_CONTRACT_ID = FundingResponseContract::class.qualifiedName!!
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<ValidatedCommand>()
        command.value.validate(tx, command.signers)
    }

    class Issue : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On funding response issuance, zero input states must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On funding response issuance, only one output state must be created."

            const val CONTRACT_RULE_STATUS =
                    "On funding response issuance, the status should be set to PENDING."

            const val CONTRACT_RULE_INVOICE_COUNT =
                    "On funding response issuance, there must be at least one referenced invoice."

            const val CONTRACT_RULE_SIGNERS =
                    "On funding response issuance, all participants must sign the transaction."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            //Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.isEmpty(CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.hasSize(1, CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_STATUS, {
                val status = it
                        .outputsOfType<FundingResponseState>()
                        .single()
                        .status

                status == FundingResponseStatus.PENDING
            })

            validationBuilder.validateWith(CONTRACT_RULE_INVOICE_COUNT, {
                val invoiceLinearIds = it
                        .outputsOfType<FundingResponseState>()
                        .single()
                        .invoiceLinearIds

                invoiceLinearIds.isNotEmpty()
            })

            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<FundingResponseState>()
                        .single()
                        .participants
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }

    class Accept : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On funding response acceptance, only one input state must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On funding response acceptance, only one output state must be created."

            const val CONTRACT_RULE_INPUT_STATUS =
                    "On funding response acceptance, the input state status should be set to PENDING."

            const val CONTRACT_RULE_OUTPUT_STATUS =
                    "On funding response acceptance, the output state status should be set to ACCEPTED."

            const val CONTRACT_RULE_SIGNERS =
                    "On funding response acceptance, all participants must sign the transaction."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.hasSize(1, CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.hasSize(1, CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_INPUT_STATUS, {
                val status = it
                        .inputsOfType<FundingResponseState>()
                        .single()
                        .status

                status == FundingResponseStatus.PENDING
            })

            validationBuilder.validateWith(CONTRACT_RULE_OUTPUT_STATUS, {
                val status = it
                        .outputsOfType<FundingResponseState>()
                        .single()
                        .status

                status == FundingResponseStatus.ACCEPTED
            })

            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .outputsOfType<FundingResponseState>()
                        .single()
                        .participants
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }

    class Reject : ValidatedCommand() {

        companion object {
            const val CONTRACT_RULE_INPUTS =
                    "On funding response rejection, only one input state must be consumed."

            const val CONTRACT_RULE_OUTPUTS =
                    "On funding response rejection, only one output state must be created."

            const val CONTRACT_RULE_INPUT_STATUS =
                    "On funding response rejection, the input state status should be set to PENDING."

            const val CONTRACT_RULE_OUTPUT_STATUS =
                    "On funding response rejection, the output state status should be set to REJECTED."

            const val CONTRACT_RULE_SIGNERS =
                    "On funding response rejection, all participants must sign the transaction."
        }

        override fun validate(validationBuilder: ContractValidationBuilder) {

            // Transaction Validation
            validationBuilder.property(LedgerTransaction::inputs, {
                it.hasSize(1, CONTRACT_RULE_INPUTS)
            })

            validationBuilder.property(LedgerTransaction::outputs, {
                it.hasSize(1, CONTRACT_RULE_OUTPUTS)
            })

            // State Validation
            validationBuilder.validateWith(CONTRACT_RULE_INPUT_STATUS, {
                val status = it
                        .inputsOfType<FundingResponseState>()
                        .single()
                        .status

                status == FundingResponseStatus.PENDING
            })

            validationBuilder.validateWith(CONTRACT_RULE_OUTPUT_STATUS, {
                val status = it
                        .outputsOfType<FundingResponseState>()
                        .single()
                        .status

                status == FundingResponseStatus.REJECTED
            })

            validationBuilder.validateWith(CONTRACT_RULE_SIGNERS, {
                val keys = it
                        .inputsOfType<FundingResponseState>()
                        .single()
                        .participants
                        .toOwningKeys()
                        .distinct()

                validationBuilder.signers.containsAll(keys)
            })
        }
    }
}
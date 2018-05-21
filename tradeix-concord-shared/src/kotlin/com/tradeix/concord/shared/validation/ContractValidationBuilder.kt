package com.tradeix.concord.shared.validation

import net.corda.core.contracts.ContractState
import net.corda.core.transactions.LedgerTransaction

class ContractValidationBuilder(validator: Validator, transaction: LedgerTransaction?)
    : ValidationBuilder<LedgerTransaction>(validator, transaction) {

    fun <TState : ContractState> validationBuilderFor(
            selector: (LedgerTransaction) -> TState?): ValidationBuilder<TState> {

        return ValidationBuilder(validator, getTransactionItem { selector(it) })
    }

    fun <TValue> getTransactionItem(
            selector: (LedgerTransaction) -> TValue): TValue? {

        return try {
            if (receiver == null) null else selector(receiver)
        } catch (ex: Exception) {
            validator.addValidationMessage(ex.message ?: "Failed to get requested item from transaction.")
            return null
        }
    }

    fun <TState : ContractState> getTransactionState(
            selector: (LedgerTransaction) -> TState): TState? {

        return try {
            if (receiver == null) null else selector(receiver)
        } catch (ex: Exception) {
            validator.addValidationMessage(ex.message ?: "Failed to get requested item from transaction.")
            return null
        }
    }

    fun <TState : ContractState> getTransactionStates(
            selector: (LedgerTransaction) -> Iterable<TState>): Iterable<TState?> {

        return try {
            if (receiver == null) listOf(null) else selector(receiver)
        } catch (ex: Exception) {
            validator.addValidationMessage(ex.message ?: "Failed to get requested item from transaction.")
            return listOf(null)
        }
    }
}
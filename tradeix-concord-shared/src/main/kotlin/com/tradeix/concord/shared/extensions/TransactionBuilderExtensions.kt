package com.tradeix.concord.shared.extensions

import net.corda.core.contracts.ContractState
import net.corda.core.contracts.StateAndRef
import net.corda.core.transactions.TransactionBuilder

fun <TContractState : ContractState> TransactionBuilder.addInputStates(
        states: Iterable<StateAndRef<TContractState>>): TransactionBuilder {
    states.forEach { this.addInputState(it) }
    return this
}

fun <TContractState : ContractState> TransactionBuilder.addOutputStates(
        states: Iterable<TContractState>, contractClassName: String): TransactionBuilder {
    states.forEach { this.addOutputState(it, contractClassName) }
    return this
}
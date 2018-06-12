package com.tradeix.concord.shared.mapper

import net.corda.core.contracts.ContractState
import net.corda.core.contracts.StateAndRef
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InputAndOutput<TState : ContractState>(val input: StateAndRef<TState>, val output: TState)
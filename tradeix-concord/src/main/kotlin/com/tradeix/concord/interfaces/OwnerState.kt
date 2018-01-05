package com.tradeix.concord.interfaces

import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party

interface OwnerState : ContractState {
    val owner: AbstractParty
}
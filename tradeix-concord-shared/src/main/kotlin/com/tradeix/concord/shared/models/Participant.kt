package com.tradeix.concord.shared.models

import net.corda.core.identity.AbstractParty
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class Participant(val party: AbstractParty?, val companyReference: String?) {

    override fun toString(): String = party.toString()
}
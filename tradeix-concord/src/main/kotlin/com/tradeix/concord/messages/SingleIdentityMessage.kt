package com.tradeix.concord.messages

import com.fasterxml.jackson.annotation.JsonIgnore
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
interface SingleIdentityMessage {
    val externalId: String?

    @JsonIgnore
    fun getLinearId() = UniqueIdentifier(externalId)
}
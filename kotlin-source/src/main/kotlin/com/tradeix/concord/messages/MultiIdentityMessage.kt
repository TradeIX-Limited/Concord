package com.tradeix.concord.messages

import com.fasterxml.jackson.annotation.JsonIgnore
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
interface MultiIdentityMessage {
    val externalIds: List<String>?

    @JsonIgnore
    fun getLinearIds() = externalIds?.map { UniqueIdentifier(it) } ?: emptyList()
}
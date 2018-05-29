package com.tradeix.concord.shared.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class OwnershipRequestMessage(
        val externalId: String? = null,
        val owner: String? = null
)
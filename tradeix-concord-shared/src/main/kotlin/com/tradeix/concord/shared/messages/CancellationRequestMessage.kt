package com.tradeix.concord.shared.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class CancellationRequestMessage(val externalId: String? = null)
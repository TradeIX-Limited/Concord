package com.tradeix.concord.shared.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class ValidationResponseMessage(val errors: Iterable<String>)
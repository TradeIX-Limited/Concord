package com.tradeix.concord.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
abstract class Message(open val correlationId: String?)
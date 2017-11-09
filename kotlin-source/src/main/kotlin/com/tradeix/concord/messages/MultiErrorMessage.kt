package com.tradeix.concord.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
interface MultiErrorMessage {
    val errorMessages: List<String>?
}
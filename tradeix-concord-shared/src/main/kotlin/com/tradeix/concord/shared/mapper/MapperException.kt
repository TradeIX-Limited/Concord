package com.tradeix.concord.shared.mapper

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
class MapperException(message: String)
    : Exception(message)
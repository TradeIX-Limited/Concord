package com.tradeix.concord.shared.client.webapi

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class RequestParameterInfo(
        val required: Boolean,
        val defaultValue: String? = null,
        val values: Collection<String>? = null
)

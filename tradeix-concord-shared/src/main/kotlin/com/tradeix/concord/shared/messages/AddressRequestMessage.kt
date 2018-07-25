package com.tradeix.concord.shared.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class AddressRequestMessage(
        val residenceNameOrNumber: String? = null,
        val unitNameOrNumber: String? = null,
        val street: String? = null,
        val locality: String? = null,
        val municipality: String? = null,
        val province: String? = null,
        val country: String? = null,
        val postalCode: String? = null
)
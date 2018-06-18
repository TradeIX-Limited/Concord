package com.tradeix.concord.shared.domain.enumerations

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
enum class FundingResponseStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}
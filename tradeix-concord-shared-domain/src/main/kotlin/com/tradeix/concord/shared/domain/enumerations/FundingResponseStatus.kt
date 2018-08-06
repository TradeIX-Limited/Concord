package com.tradeix.concord.shared.domain.enumerations

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
enum class FundingResponseStatus(name: String) {
    PENDING("DecisionPending"),
    ACCEPTED("Accepted"),
    REJECTED("Declined");

    override fun toString(): String = name
}
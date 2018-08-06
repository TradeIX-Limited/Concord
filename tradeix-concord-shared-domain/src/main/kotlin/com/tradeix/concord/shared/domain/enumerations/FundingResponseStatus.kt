package com.tradeix.concord.shared.domain.enumerations

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
enum class FundingResponseStatus(private val alternativeName: String) {
    PENDING("DecisionPending"),
    ACCEPTED("Accepted"),
    REJECTED("Declined");

    override fun toString(): String = alternativeName
}
package com.tradeix.concord.messages.webapi.membership

data class MembershipsResponseMessage(
        val linearId: String,
        val member: String,
        val status: String
)
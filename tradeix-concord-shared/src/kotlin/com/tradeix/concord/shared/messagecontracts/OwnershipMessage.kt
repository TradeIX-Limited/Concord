package com.tradeix.concord.shared.messagecontracts

interface OwnershipMessage {
    val externalId: String?
    val owner: String?
}
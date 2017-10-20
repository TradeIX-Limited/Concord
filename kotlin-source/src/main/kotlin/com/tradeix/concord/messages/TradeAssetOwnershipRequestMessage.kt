package com.tradeix.concord.messages

import net.corda.core.identity.CordaX500Name
import java.util.UUID

data class TradeAssetOwnershipRequestMessage(
        val linearId: UUID?,
        val newOwner: CordaX500Name?) : RequestMessage() {

    companion object {
        private val EX_LINEAR_ID_MSG = "Linear ID is required for an a change of ownership transaction"
        private val EX_NEW_OWNER_MSG = "New owner is required for an a change of ownership transaction"
    }

    override fun getValidationErrors(): ArrayList<String> {
        val result = ArrayList<String>()

        linearId ?: result.add(EX_LINEAR_ID_MSG)
        newOwner ?: result.add(EX_NEW_OWNER_MSG)

        return result
    }
}
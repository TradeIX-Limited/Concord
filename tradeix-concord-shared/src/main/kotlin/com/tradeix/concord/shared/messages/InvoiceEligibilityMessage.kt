package com.tradeix.concord.shared.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceEligibilityMessage(
        val invoiceId: String? = null,
        val supplier: String? = null,
        val eligible: Boolean? = null
)
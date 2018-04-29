package com.tradeix.concord.flowmodels.invoice

import com.tradeix.concord.messages.SingleIdentityMessage

data class InvoiceCancellationFlowModel(
        override val externalId: String?
) : SingleIdentityMessage
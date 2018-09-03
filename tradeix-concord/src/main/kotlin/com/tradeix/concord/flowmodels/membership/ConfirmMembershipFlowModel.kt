package com.tradeix.concord.flowmodels.membership

import com.tradeix.concord.messages.SingleIdentityMessage

data class ConfirmMembershipFlowModel(
        override val externalId: String?,
        val attachmentId: String?
) : SingleIdentityMessage
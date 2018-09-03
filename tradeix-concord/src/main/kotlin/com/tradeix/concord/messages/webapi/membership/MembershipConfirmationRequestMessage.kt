package com.tradeix.concord.messages.webapi.membership

import com.tradeix.concord.flowmodels.membership.ConfirmMembershipFlowModel
import com.tradeix.concord.messages.AttachmentMessage
import com.tradeix.concord.messages.SingleIdentityMessage

class MembershipConfirmationRequestMessage(
        override val externalId: String? = null,
        override val attachmentId: String? = null
) : SingleIdentityMessage, AttachmentMessage {
    fun toModel() = ConfirmMembershipFlowModel(
            externalId,
            attachmentId
    )
}

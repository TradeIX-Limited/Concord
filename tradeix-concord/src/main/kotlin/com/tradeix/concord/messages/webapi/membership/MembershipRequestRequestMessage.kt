package com.tradeix.concord.messages.webapi.membership

import com.tradeix.concord.flowmodels.membership.RequestMembershipFlowModel
import com.tradeix.concord.messages.AttachmentMessage
import com.tradeix.concord.messages.SingleIdentityMessage
import net.corda.businessnetworks.membership.states.MembershipMetadata

class MembershipRequestRequestMessage(
        override val externalId: String? = null,
        override val attachmentId: String? = null,
        val metadata: MembershipMetadata? = null
) : SingleIdentityMessage, AttachmentMessage {
    fun toModel() = RequestMembershipFlowModel(
            externalId,
            attachmentId,
            metadata
    )
}
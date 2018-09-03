package com.tradeix.concord.flowmodels.membership

import com.tradeix.concord.messages.SingleIdentityMessage
import net.corda.businessnetworks.membership.states.MembershipMetadata

class RequestMembershipFlowModel(
        override val externalId: String?,
        val attachmentId: String?,
        val metadata: MembershipMetadata?
) : SingleIdentityMessage

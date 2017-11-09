package com.tradeix.concord.messages.rabbit.tradeasset

import com.tradeix.concord.messages.AttachmentMessage
import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import net.corda.core.identity.CordaX500Name
import java.math.BigDecimal

class TradeAssetIssuanceRequestMessage(
        override val correlationId: String?,
        override val tryCount: Int,
        override val externalId: String?,
        override val attachmentId: String?,
        val buyer: CordaX500Name?,
        val supplier: CordaX500Name?,
        val conductor: CordaX500Name = CordaX500Name("TradeIX", "London", "GB"),
        val status: String?,
        val value: BigDecimal?,
        val currency: String?
) : RabbitRequestMessage(correlationId, tryCount), SingleIdentityMessage, AttachmentMessage
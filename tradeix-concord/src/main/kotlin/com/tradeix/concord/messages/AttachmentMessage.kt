package com.tradeix.concord.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
interface AttachmentMessage {
    val attachmentId: String?
}
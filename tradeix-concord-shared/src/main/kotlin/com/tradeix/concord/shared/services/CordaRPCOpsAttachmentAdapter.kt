package com.tradeix.concord.shared.services

import net.corda.core.crypto.SecureHash
import net.corda.core.messaging.CordaRPCOps
import java.io.InputStream
import java.util.*

internal class CordaRPCOpsAttachmentAdapter(private val rpcOps: CordaRPCOps) : AttachmentAdapter() {
    override fun hasAttachment(id: SecureHash): Boolean {
        return rpcOps.attachmentExists(id)
    }

    override fun getAttachment(id: SecureHash): InputStream? {
        return rpcOps.openAttachment(id)
    }

    override fun addAttachment(jar: InputStream, uploader: String, fileName: String?): SecureHash {
        return rpcOps.uploadAttachmentWithMetadata(jar, uploader, fileName ?: UUID.randomUUID().toString())
    }
}
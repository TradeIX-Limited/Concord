package com.tradeix.concord.shared.data

import net.corda.core.crypto.SecureHash
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.node.ServiceHub
import java.io.InputStream

class AttachmentRepository(private val attachmentAdapter: AttachmentAdapter) {

    companion object {
        fun fromServiceHub(serviceHub: ServiceHub): AttachmentRepository {
            return AttachmentRepository(AttachmentAdapter.fromServiceHub(serviceHub))
        }

        fun fromCordaRPCOps(rpcOps: CordaRPCOps): AttachmentRepository {
            return AttachmentRepository(AttachmentAdapter.fromCordaRPCOps(rpcOps))
        }
    }

    fun hasAttachment(id: SecureHash): Boolean {
        return attachmentAdapter.hasAttachment(id)
    }

    fun getAttachment(id: SecureHash): InputStream? {
        return attachmentAdapter.getAttachment(id)
    }

    fun addAttachment(jar: InputStream, uploader: String, fileName: String? = null): SecureHash {
        return attachmentAdapter.addAttachment(jar, uploader, fileName)
    }
}
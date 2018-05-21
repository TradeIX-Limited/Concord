package com.tradeix.concord.shared.data

import net.corda.core.crypto.SecureHash
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.node.ServiceHub
import java.io.InputStream

abstract class AttachmentAdapter {

    companion object {
        fun fromServiceHub(serviceHub: ServiceHub): AttachmentAdapter {
            return ServiceHubAttachmentAdapter(serviceHub)
        }

        fun fromCordaRPCOps(rpcOps: CordaRPCOps): AttachmentAdapter {
            return CordaRPCOpsAttachmentAdapter(rpcOps)
        }
    }

    abstract fun hasAttachment(id: SecureHash): Boolean
    abstract fun getAttachment(id: SecureHash): InputStream?
    abstract fun addAttachment(jar: InputStream, uploader: String, fileName: String? = null): SecureHash
}
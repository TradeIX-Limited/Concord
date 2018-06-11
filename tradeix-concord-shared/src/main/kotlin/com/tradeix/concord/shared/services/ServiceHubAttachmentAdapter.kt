package com.tradeix.concord.shared.services

import net.corda.core.crypto.SecureHash
import net.corda.core.node.ServiceHub
import java.io.InputStream

internal class ServiceHubAttachmentAdapter(private val serviceHub: ServiceHub) : AttachmentAdapter() {

    override fun hasAttachment(id: SecureHash): Boolean {
        return serviceHub.attachments.hasAttachment(id)
    }

    override fun getAttachment(id: SecureHash): InputStream? {
        return serviceHub.attachments.openAttachment(id)?.open()
    }

    override fun addAttachment(jar: InputStream, uploader: String, fileName: String?): SecureHash {
        return serviceHub.attachments.importAttachment(jar, uploader, fileName)
    }
}
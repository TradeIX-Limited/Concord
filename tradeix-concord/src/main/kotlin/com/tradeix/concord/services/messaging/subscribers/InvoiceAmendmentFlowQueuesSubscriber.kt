package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.tradeix.concord.messages.rabbit.invoice.InvoiceAmendmentRequestMessage
import com.typesafe.config.Config
import net.corda.core.messaging.CordaRPCOps

class InvoiceAmendmentFlowQueuesSubscriber(
        cordaRpcService: CordaRPCOps,
        config: Config,
        serializer: Gson
) : FlowQueuesSubscriber<InvoiceAmendmentRequestMessage>(
        cordaRpcService,
        config,
        serializer,
        "tix-integration",
        "invoiceAmendmentConsumeConfiguration",
        "invoiceAmendmentDeadLetterConfiguration",
        "invoiceAmendmentResponseConfiguration",
        "cordatix_amend_invoice_response",
        InvoiceAmendmentRequestMessage::class.java
)
package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.tradeix.concord.messages.rabbit.invoice.InvoiceIssuanceRequestMessage
import com.typesafe.config.Config
import net.corda.core.messaging.CordaRPCOps

class InvoiceIssuanceFlowQueuesSubscriber(
        cordaRpcService: CordaRPCOps,
        config: Config,
        serializer: Gson
) : FlowQueuesSubscriber<InvoiceIssuanceRequestMessage>(
        cordaRpcService,
        config,
        serializer,
        "tix-integration",
        "invoiceIssuanceConsumeConfiguration",
        "invoiceIssuanceDeadLetterConfiguration",
        "invoiceIssuanceResponseConfiguration",
        "cordatix_issue_invoice_response",
        InvoiceIssuanceRequestMessage::class.java
)
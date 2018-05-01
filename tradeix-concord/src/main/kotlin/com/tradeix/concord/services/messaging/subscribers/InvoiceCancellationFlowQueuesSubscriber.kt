package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.tradeix.concord.messages.rabbit.invoice.InvoiceCancellationRequestMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderCancellationRequestMessage
import com.typesafe.config.Config
import net.corda.core.messaging.CordaRPCOps

class InvoiceCancellationFlowQueuesSubscriber(
        cordaRpcService: CordaRPCOps,
        config: Config,
        serializer: Gson
) : FlowQueuesSubscriber<InvoiceCancellationRequestMessage>(
        cordaRpcService,
        config,
        serializer,
        "tix-integration",
        "invoiceCancellationConsumeConfiguration",
        "invoiceCancellationDeadLetterConfiguration",
        "invoiceCancellationResponseConfiguration",
        "cordatix_cancel_invoice_response",
        InvoiceCancellationRequestMessage::class.java
)
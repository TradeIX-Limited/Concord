package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.tradeix.concord.messages.rabbit.invoice.InvoiceOwnershipRequestMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderOwnershipRequestMessage
import com.typesafe.config.Config
import net.corda.core.messaging.CordaRPCOps

class InvoiceChangeOwnerFlowQueuesSubscriber(
        cordaRpcService: CordaRPCOps,
        config: Config,
        serializer: Gson
) : FlowQueuesSubscriber<InvoiceOwnershipRequestMessage>(
        cordaRpcService,
        config,
        serializer,
        "tix-integration",
        "invoiceChangeOwnerConsumeConfiguration",
        "invoiceChangeOwnerDeadLetterConfiguration",
        "invoiceChangeOwnerResponseConfiguration",
        "cordatix_change_owner_invoice_response",
        InvoiceOwnershipRequestMessage::class.java
)
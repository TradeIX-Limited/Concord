package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderCancellationRequestMessage
import com.typesafe.config.Config
import net.corda.core.messaging.CordaRPCOps

class PurchaseOrderCancellationFlowQueuesSubscriber(
        cordaRpcService: CordaRPCOps,
        config: Config,
        serializer: Gson
) : FlowQueuesSubscriber<PurchaseOrderCancellationRequestMessage>(
        cordaRpcService,
        config,
        serializer,
        "tix-integration",
        "purchaseOrderCancellationConsumeConfiguration",
        "purchaseOrderCancellationDeadLetterConfiguration",
        "purchaseOrderCancellationResponseConfiguration",
        "cordatix_cancel_purchase_order_response",
        PurchaseOrderCancellationRequestMessage::class.java
)
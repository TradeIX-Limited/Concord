package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderIssuanceRequestMessage
import com.typesafe.config.Config
import net.corda.core.messaging.CordaRPCOps

class PurchaseOrderIssuanceFlowQueuesSubscriber(
        cordaRpcService: CordaRPCOps,
        config: Config,
        serializer: Gson
) : FlowQueuesSubscriber<PurchaseOrderIssuanceRequestMessage>(
        cordaRpcService,
        config,
        serializer,
        "tix-integration",
        "purchaseOrderIssuanceConsumeConfiguration",
        "purchaseOrderIssuanceDeadLetterConfiguration",
        "purchaseOrderIssuanceResponseConfiguration",
        "cordatix_issue_purchase_order_response",
        PurchaseOrderIssuanceRequestMessage::class.java
)
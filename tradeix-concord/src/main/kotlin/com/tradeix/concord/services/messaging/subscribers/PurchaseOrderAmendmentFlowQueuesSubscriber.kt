package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderAmendmentRequestMessage
import com.typesafe.config.Config
import net.corda.core.messaging.CordaRPCOps

class PurchaseOrderAmendmentFlowQueuesSubscriber(
        cordaRpcService: CordaRPCOps,
        config: Config,
        serializer: Gson
) : FlowQueuesSubscriber<PurchaseOrderAmendmentRequestMessage>(
        cordaRpcService,
        config,
        serializer,
        "tix-integration",
        "purchaseOrderAmendmentConsumeConfiguration",
        "purchaseOrderAmendmentDeadLetterConfiguration",
        "purchaseOrderAmendmentResponseConfiguration",
        "cordatix_amend_purchase_order_response",
        PurchaseOrderAmendmentRequestMessage::class.java
)
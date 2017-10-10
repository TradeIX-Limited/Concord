package com.tradeix.concord.api

import com.tradeix.concord.flow.PurchaseOrderIssuanceFlow
import com.tradeix.concord.messages.ErrorResponseMessage
import com.tradeix.concord.messages.IssuePurchaseOrderRequestMessage
import com.tradeix.concord.messages.IssuePurchaseOrderResponseMessage
import com.tradeix.concord.state.PurchaseOrderState
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.utilities.getOrThrow
import net.corda.finance.POUNDS
import net.corda.finance.contracts.asset.Obligation
import java.math.BigDecimal
import java.util.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("purchaseorders")
class PurchaseOrderApi(val services: CordaRPCOps) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllPurchaseOrders(): Response = Response
            .status(Response.Status.OK)
            .entity(services.vaultQueryBy<PurchaseOrderState>().states)
            .build()

    @POST
    @Path("issue")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun issuePurchaseOrder(message: IssuePurchaseOrderRequestMessage): Response {

        if(message.amount == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseMessage("Cannot issue a purchase order without an amount."))
                    .build()
        }

        if(message.currency == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseMessage("Cannot issue a purchase order without a currency."))
                    .build()
        }

        if (message.supplier == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseMessage("Cannot issue a purchase order without a known supplier."))
                    .build()
        }

        val amount = Amount.fromDecimal<Currency>(message.amount, Currency.getInstance(message.currency))

        if (amount < 0.POUNDS) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseMessage("Cannot issue a purchase order with a negative value."))
                    .build()
        }

        val linearId = UniqueIdentifier()
        val buyer = services.nodeInfo().legalIdentities[0]

        val supplier = services.wellKnownPartyFromX500Name(message.supplier) ?:
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(ErrorResponseMessage("Failed to issue purchase order. Could not find supplier."))
                        .build()

        val conductor = services.wellKnownPartyFromX500Name(CordaX500Name("TradeIX", "London", "GB")) ?:
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(ErrorResponseMessage("Failed to issue purchase order. Could not find conductor."))
                        .build()

        try {

            val flowHandle = services.startTrackedFlow(
                    PurchaseOrderIssuanceFlow::Initiator,
                    linearId,
                    amount,
                    buyer,
                    supplier,
                    conductor)

            flowHandle.progress.subscribe { println(">> $it") }

            val result = flowHandle
                    .returnValue
                    .getOrThrow()

            return Response
                    .status(Response.Status.CREATED)
                    .entity(IssuePurchaseOrderResponseMessage(
                            transactionId = result.id.toString(),
                            linearId = linearId.id.toString()))
                    .build()

        } catch(ex: Throwable) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseMessage(ex.message!!))
                    .build()
        }
    }
}

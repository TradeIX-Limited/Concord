package com.tradeix.concord.apis

import com.tradeix.concord.flows.PurchaseOrderIssuance
import com.tradeix.concord.flows.PurchaseOrderOwnership
import com.tradeix.concord.messages.*
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.utilities.getOrThrow
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

    @PUT
    @Path("changeowner")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun changePurchaseOrderOwner(message: PurchaseOrderOwnershipRequestMessage): Response {

        if(message.linearId == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseMessage("No linearId given for purchase order ownership change."))
                    .build()
        }

        if(message.newOwner == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseMessage("No newOwner given for purchase order ownership change."))
                    .build()
        }

        val newOwner = services.wellKnownPartyFromX500Name(message.newOwner) ?:
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(ErrorResponseMessage("Failed to find new owner party for ownership change."))
                        .build()

        val linearId = UniqueIdentifier(id = message.linearId)

        val inputStateAndRef = services.vaultQueryByCriteria(
                QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId)),
                PurchaseOrderState::class.java).states.single()

        try {
            val flowHandle = services.startTrackedFlow(
                    PurchaseOrderOwnership::BuyerFlow,
                    inputStateAndRef,
                    newOwner)

            flowHandle.progress.subscribe { println(">> $it") }

            val result = flowHandle
                    .returnValue
                    .getOrThrow()

            return Response
                    .status(Response.Status.CREATED)
                    .entity(TransactionResponseMessage(result.id.toString()))
                    .build()

        } catch(ex: Throwable) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseMessage(ex.message!!))
                    .build()
        }
    }

    @POST
    @Path("issue")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun issuePurchaseOrder(message: PurchaseOrderIssuanceRequestMessage): Response {

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

        if (amount.toDecimal() < BigDecimal.ZERO) {
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
                    PurchaseOrderIssuance::BuyerFlow,
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
                    .entity(LinearTransactionResponseMessage(
                            linearId = linearId.id.toString(),
                            transactionId = result.id.toString()))
                    .build()

        } catch(ex: Throwable) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseMessage(ex.message!!))
                    .build()
        }
    }
}

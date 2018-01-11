package com.tradeix.concord.apis

import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.extensions.CordaRPCOpsExtensions.vaultCountBy
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderIssuance
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderOwnership
import com.tradeix.concord.messages.webapi.FailedResponseMessage
import com.tradeix.concord.messages.webapi.FailedValidationResponseMessage
import com.tradeix.concord.messages.webapi.MultiIdentitySuccessResponseMessage
import com.tradeix.concord.messages.webapi.SingleIdentitySuccessResponseMessage
import com.tradeix.concord.messages.webapi.purchaseorder.PurchaseOrderIssuanceRequestMessage
import com.tradeix.concord.messages.webapi.purchaseorder.PurchaseOrderOwnershipRequestMessage
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.PageSpecification
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.utilities.getOrThrow
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("purchaseorders")
class PurchaseOrderApi(val services: CordaRPCOps) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getPurchaseOrderSatet(@QueryParam(value = "externalId") externalId: String): Response {
        if (externalId.isEmpty() || externalId.isBlank()) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(IllegalArgumentException("externalId is required to query a purchase order state"))
                    .build()
        }

        val criteria = QueryCriteria.LinearStateQueryCriteria(
                externalId = listOf(externalId),
                status = Vault.StateStatus.ALL)

        return Response
                .status(Response.Status.OK)
                .entity(services.vaultQueryBy<PurchaseOrderState>(criteria = criteria).states)
                .build()
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    fun getPurchaseOrderCount(): Response {
        return Response
                .status(Response.Status.OK)
                .entity(mapOf("count" to services.vaultCountBy<PurchaseOrderState>()))
                .build()
    }

    @GET
    @Path("hash")
    @Produces(MediaType.APPLICATION_JSON)
    fun getMostRecentPurchaseOrderHash(): Response {
        return try {
            Response
                    .status(Response.Status.OK)
                    .entity(mapOf("hash" to services.vaultQueryBy<PurchaseOrderState>(
                            paging = PageSpecification(1, Int.MAX_VALUE)).states.last().ref.txhash))
                    .build()
        } catch (ex: Throwable) {
            Response
                    .status(Response.Status.OK)
                    .entity(mapOf("hash" to null))
                    .build()
        }
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllPurchaseOrders(
            @QueryParam(value = "page") page: Int,
            @QueryParam(value = "count") count: Int): Response {

        val pageNumber = if (page == 0) 1 else page
        val pageSize = if (count == 0) 50 else count

        return Response
                .status(Response.Status.OK)
                .entity(services.vaultQueryBy<PurchaseOrderState>(
                        paging = PageSpecification(pageNumber, pageSize)).states.asReversed())
                .build()
    }

    @POST
    @Path("issue")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun issuePurchaseOrder(message: PurchaseOrderIssuanceRequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(PurchaseOrderIssuance::InitiatorFlow, message.toModel())
            flowHandle.progress.subscribe { println(">> $it") }
            val result = flowHandle.returnValue.getOrThrow()
            return Response
                    .status(Response.Status.CREATED)
                    .entity(SingleIdentitySuccessResponseMessage(
                            externalId = message.externalId!!,
                            transactionId = result.id.toString()))
                    .build()
        } catch (ex: Throwable) {
            return when (ex) {
                is FlowValidationException -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(FailedValidationResponseMessage(ex.validationErrors))
                        .build()
                else -> Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(FailedResponseMessage(ex.message!!))
                        .build()
            }
        }
    }

    @PUT
    @Path("changeowner")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun changePurchaseOrderOwnership(message: PurchaseOrderOwnershipRequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(PurchaseOrderOwnership::InitiatorFlow, message.toModel())
            flowHandle.progress.subscribe { println(">> $it") }
            val result = flowHandle.returnValue.getOrThrow()
            return Response
                    .status(Response.Status.OK)
                    .entity(MultiIdentitySuccessResponseMessage(
                            externalIds = message.externalIds!!,
                            transactionId = result.id.toString()))
                    .build()
        } catch (ex: Throwable) {
            return when (ex) {
                is FlowValidationException -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(FailedValidationResponseMessage(ex.validationErrors))
                        .build()
                else -> Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(FailedResponseMessage(ex.message!!))
                        .build()
            }
        }
    }
}
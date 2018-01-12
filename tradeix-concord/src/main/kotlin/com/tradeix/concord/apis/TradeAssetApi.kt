package com.tradeix.concord.apis

import com.tradeix.concord.flows.tradeasset.TradeAssetAmendment
import com.tradeix.concord.flows.tradeasset.TradeAssetCancellation
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flows.tradeasset.TradeAssetIssuance
import com.tradeix.concord.flows.tradeasset.TradeAssetOwnership
import com.tradeix.concord.messages.webapi.FailedResponseMessage
import com.tradeix.concord.messages.webapi.FailedValidationResponseMessage
import com.tradeix.concord.messages.webapi.MultiIdentitySuccessResponseMessage
import com.tradeix.concord.messages.webapi.SingleIdentitySuccessResponseMessage
import com.tradeix.concord.messages.webapi.tradeasset.TradeAssetAmendmentRequestMessage
import com.tradeix.concord.messages.webapi.tradeasset.TradeAssetCancellationRequestMessage
import com.tradeix.concord.messages.webapi.tradeasset.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.messages.webapi.tradeasset.TradeAssetOwnershipRequestMessage
import com.tradeix.concord.states.TradeAssetState
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
import com.tradeix.concord.extensions.CordaRPCOpsExtensions.vaultCountBy

@Path("tradeassets")
class TradeAssetApi(val services: CordaRPCOps) {

    companion object {
        val DEFAULT_PAGE_SIZE = 50
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getTradeAsset(@QueryParam(value = "externalId") externalId: String): Response {
        if (externalId.isEmpty() || externalId.isBlank()) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(IllegalArgumentException("externalId is required to query a trade asset state"))
                    .build()
        }

        val criteria = QueryCriteria.LinearStateQueryCriteria(
                externalId = listOf(externalId),
                status = Vault.StateStatus.ALL)

        return Response
                .status(Response.Status.OK)
                .entity(services.vaultQueryBy<TradeAssetState>(criteria = criteria).states)
                .build()
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTradeAssetCount(): Response {
        return Response
                .status(Response.Status.OK)
                .entity(mapOf("count" to services.vaultCountBy<TradeAssetState>()))
                .build()
    }

    @GET
    @Path("hash")
    @Produces(MediaType.APPLICATION_JSON)
    fun getMostRecentTradeAssetHash(): Response {
        return try {
            Response
                    .status(Response.Status.OK)
                    .entity(mapOf("hash" to services.vaultQueryBy<TradeAssetState>(
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
    fun getAllTradeAssets(
            @QueryParam(value = "page") page: Int,
            @QueryParam(value = "count") count: Int): Response {

        val pageNumber = if (page <= 0) 1 else page
        val pageSize = if (count <= 0) DEFAULT_PAGE_SIZE else count

        return try {
            Response
                    .status(Response.Status.OK)
                    .entity(services.vaultQueryBy<TradeAssetState>(
                            paging = PageSpecification(pageNumber, pageSize)).states.asReversed())
                    .build()
        } catch(ex: Throwable) {
            Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(FailedResponseMessage(ex.message!!))
                    .build()
        }
    }

    @POST
    @Path("issue")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun issueTradeAsset(message: TradeAssetIssuanceRequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(TradeAssetIssuance::InitiatorFlow, message.toModel())
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
    fun changeTradeAssetOwner(message: TradeAssetOwnershipRequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(TradeAssetOwnership::InitiatorFlow, message.toModel())
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

    @PUT
    @Path("cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun cancelTradeAsset(message: TradeAssetCancellationRequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(TradeAssetCancellation::InitiatorFlow, message.toModel())
            flowHandle.progress.subscribe { println(">> $it") }
            val result = flowHandle.returnValue.getOrThrow()
            return Response
                    .status(Response.Status.OK)
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
    @Path("amend")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun amendTradeAsset(message: TradeAssetAmendmentRequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(TradeAssetAmendment::InitiatorFlow, message.toModel())
            flowHandle.progress.subscribe { println(">> $it") }
            val result = flowHandle.returnValue.getOrThrow()
            return Response
                    .status(Response.Status.OK)
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
}
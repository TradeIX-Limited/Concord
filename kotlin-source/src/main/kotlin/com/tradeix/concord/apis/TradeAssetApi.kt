package com.tradeix.concord.apis

import com.tradeix.concord.flows.TradeAssetAmendment
import com.tradeix.concord.flows.TradeAssetCancellation
import com.tradeix.concord.exceptions.ValidationException
import com.tradeix.concord.flows.TradeAssetIssuance
import com.tradeix.concord.flows.TradeAssetOwnership
import com.tradeix.concord.messages.*
import com.tradeix.concord.states.TradeAssetState
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.utilities.getOrThrow
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("tradeassets")
class TradeAssetApi(val services: CordaRPCOps) {

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllTradeAssets(): Response = Response
            .status(Response.Status.OK)
            .entity(services.vaultQueryBy<TradeAssetState>().states)
            .build()

    @POST
    @Path("issue")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun issueTradeAsset(message: TradeAssetIssuanceRequestMessage): Response {
        try {

            val flowHandle = services.startTrackedFlow(TradeAssetIssuance::InitiatorFlow, message)
            flowHandle.progress.subscribe { println(">> $it") }
            val result = flowHandle.returnValue.getOrThrow()

            return Response
                    .status(Response.Status.CREATED)
                    .entity(LinearTransactionResponseMessage(message.linearId.toString(), result.id.toString()))
                    .build()

        } catch (ex: Throwable) {
            return when (ex) {
                is ValidationException -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(ErrorsResponseMessage(ex.validationErrors))
                        .build()
                else -> Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(ErrorResponseMessage(ex.message!!))
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

            val flowHandle = services.startTrackedFlow(TradeAssetOwnership::InitiatorFlow, message)
            flowHandle.progress.subscribe { println(">> $it") }
            val result = flowHandle.returnValue.getOrThrow()

            return Response
                    .status(Response.Status.OK)
                    .entity(TransactionResponseMessage(result.id.toString()))
                    .build()

        } catch (ex: Throwable) {
            return when (ex) {
                is ValidationException -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(ErrorsResponseMessage(ex.validationErrors))
                        .build()
                else -> Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(ErrorResponseMessage(ex.message!!))
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
            val flowHandle = services.startTrackedFlow(TradeAssetCancellation::InitiatorFlow, message)
            flowHandle.progress.subscribe { println(">> $it") }
            val result = flowHandle.returnValue.getOrThrow()

            return Response
                    .status(Response.Status.OK)
                    .entity(TransactionResponseMessage(result.id.toString()))
                    .build()

        } catch (ex: Throwable) {
            return when (ex) {
                is ValidationException -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(ErrorsResponseMessage(ex.validationErrors))
                        .build()
                else -> Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(ErrorResponseMessage(ex.message!!))
                        .build()
            }
        }
    }

    // TODO : Should be PATCH but this web-api sucks!
    @PUT
    @Path("amend")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun AmendTradeAsset(message: TradeAssetAmendmentRequestMessage): Response {

        if (message.linearId == null) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponseMessage("No linearId given for purchase order ownership change."))
                    .build()
        }

        if(message.amount == null && message.assetId == null && message.currency == null) {
            return Response
                    .status(Response.Status.NOT_MODIFIED)
                    .entity(ErrorResponseMessage("Nothing to update"))
                    .build()
        }

        val linearId = UniqueIdentifier(id = message.linearId)

        val inputStateAndRef = services.vaultQueryByCriteria(
                criteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(linearId)),
                contractStateType = TradeAssetState::class.java).states.single()

        val originalTradeAsset = inputStateAndRef.state.data.tradeAsset

        val currency = if(message.currency != null) {
            Currency.getInstance(message.currency)
        } else {
            originalTradeAsset.amount.token
        }

        val amount = if(message.amount != null) {
            Amount.fromDecimal(message.amount, currency)
        } else {
            originalTradeAsset.amount
        }

        val amendedTradeAsset = originalTradeAsset.copy(
                assetId = message.assetId ?: originalTradeAsset.assetId,
                amount = amount)

        try {
            val flowHandle = services.startTrackedFlow(
                    TradeAssetAmendment::InitiatorFlow,
                    inputStateAndRef,
                    amendedTradeAsset)

            flowHandle.progress.subscribe { println(">> $it") }

            val result = flowHandle
                    .returnValue
                    .getOrThrow()

            return Response
                    .status(Response.Status.ACCEPTED)
                    .entity(TransactionResponseMessage(result.id.toString()))
                    .build()

        } catch (ex: Throwable) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ErrorResponseMessage(ex.message!!))
                    .build()
        }
    }
}
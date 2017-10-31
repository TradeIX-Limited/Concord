package com.tradeix.concord.apis

import com.tradeix.concord.flows.TradeAssetAmendment
import com.tradeix.concord.flows.TradeAssetCancellation
import com.tradeix.concord.exceptions.FlowValidationException
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
                    .entity(LinearTransactionResponseMessage(
                            correlationId = message.correlationId!!,
                            transactionId = result.id.toString(),
                            linearId = message.linearId
                    ))
                    .build()

        } catch (ex: Throwable) {
            return when (ex) {
                is FlowValidationException -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(ValidationErrorResponseMessage(
                                correlationId = message.correlationId!!,
                                errorMessage = ex.message,
                                validationErrors = ex.validationErrors))
                        .build()
                else -> Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(ErrorResponseMessage(
                                correlationId = message.correlationId!!,
                                errorMessage = ex.message!!))
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
                    .entity(TransactionResponseMessage(
                            correlationId = message.correlationId!!,
                            transactionId = result.id.toString()
                    ))
                    .build()

        } catch (ex: Throwable) {
            return when (ex) {
                is FlowValidationException -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(ValidationErrorResponseMessage(
                                correlationId = message.correlationId!!,
                                errorMessage = ex.message,
                                validationErrors = ex.validationErrors))
                        .build()
                else -> Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(ErrorResponseMessage(
                                correlationId = message.correlationId!!,
                                errorMessage = ex.message!!))
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
                    .entity(TransactionResponseMessage(
                            correlationId = message.correlationId!!,
                            transactionId = result.id.toString()
                    ))
                    .build()

        } catch (ex: Throwable) {
            return when (ex) {
                is FlowValidationException -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(ValidationErrorResponseMessage(
                                correlationId = message.correlationId!!,
                                errorMessage = ex.message,
                                validationErrors = ex.validationErrors))
                        .build()
                else -> Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(ErrorResponseMessage(
                                correlationId = message.correlationId!!,
                                errorMessage = ex.message!!))
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
            val flowHandle = services.startTrackedFlow(TradeAssetAmendment::InitiatorFlow, message)

            flowHandle.progress.subscribe { println(">> $it") }

            val result = flowHandle
                    .returnValue
                    .getOrThrow()

            return Response
                    .status(Response.Status.OK)
                    .entity(TransactionResponseMessage(
                            correlationId = message.correlationId!!,
                            transactionId = result.id.toString()
                    ))
                    .build()

        } catch (ex: Throwable) {
            return when (ex) {
                is FlowValidationException -> Response
                        .status(Response.Status.BAD_REQUEST)
                        .entity(ValidationErrorResponseMessage(
                                correlationId = message.correlationId!!,
                                errorMessage = ex.message,
                                validationErrors = ex.validationErrors))
                        .build()
                else -> Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(ErrorResponseMessage(
                                correlationId = message.correlationId!!,
                                errorMessage = ex.message!!))
                        .build()
            }
        }
    }
}
package com.tradeix.concord.apis

import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.extensions.CordaRPCOpsExtensions.vaultCountBy
import com.tradeix.concord.flows.invoice.*
import com.tradeix.concord.messages.webapi.FailedResponseMessage
import com.tradeix.concord.messages.webapi.FailedValidationResponseMessage
import com.tradeix.concord.messages.webapi.MultiIdentitySuccessResponseMessage
import com.tradeix.concord.messages.webapi.SingleIdentitySuccessResponseMessage
import com.tradeix.concord.messages.webapi.invoice.*
import com.tradeix.concord.states.InvoiceState
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

@Path("invoices")
class InvoiceApi(val services: CordaRPCOps) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getInvoiceState(@QueryParam(value = "externalId") externalId: String): Response {
        externalId.trim()
        if (externalId.trim().isEmpty()) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(IllegalArgumentException("externalId is required to query an invoice state"))
                    .build()
        }

        val criteria = QueryCriteria.LinearStateQueryCriteria(
                externalId = listOf(externalId),
                status = Vault.StateStatus.ALL)

        return Response
                .status(Response.Status.OK)
                .entity(services.vaultQueryBy<InvoiceState>(criteria = criteria).states)
                .build()
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    fun getInvoiceCount(): Response {
        return Response
                .status(Response.Status.OK)
                .entity(mapOf("count" to services.vaultCountBy<InvoiceState>()))
                .build()
    }

    @GET
    @Path("hash")
    @Produces(MediaType.APPLICATION_JSON)
    fun getMostRecentInvoiceHash(): Response {
        return try {
            Response
                    .status(Response.Status.OK)
                    .entity(mapOf("hash" to services.vaultQueryBy<InvoiceState>(
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
    fun getAllInvoices(
            @QueryParam(value = "page") page: Int,
            @QueryParam(value = "count") count: Int): Response {

        val pageNumber = if (page == 0) 1 else page
        val pageSize = if (count == 0) 50 else count

        return Response
                .status(Response.Status.OK)
                .entity(services.vaultQueryBy<InvoiceState>(
                        paging = PageSpecification(pageNumber, pageSize)).states.asReversed())
                .build()
    }

    @POST
    @Path("issue")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun issueInvoice(message: InvoiceIssuanceRequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(InvoiceIssuance::InitiatorFlow, message.toModel())
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

  @POST
  @Path("issuewithnotary")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  fun issueInvoiceWithNotary(message: InvoiceIssuanceRequestMessage): Response {
    try {
      val flowHandle = services.startTrackedFlow(InvoiceIssuance::InitiatorFlow, message.toModel())
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
    fun changeInvoiceOwner(message: InvoiceOwnershipRequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(InvoiceOwnership::InitiatorFlow, message.toModel())
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
    @Path("amend")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun amendInvoice(message: InvoiceAmendmentRequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(InvoiceAmendment::InitiatorFlow, message.toModel())
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
    @Path("cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun cancelInvoice(message: InvoiceCancellationRequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(InvoiceCancellation::InitiatorFlow, message.toModel())
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
    @Path("ipu")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun ipuInvoice(message: InvoiceIPURequestMessage): Response {
        try {
            val flowHandle = services.startTrackedFlow(InvoiceIPU::InitiatorFlow, message.toModel())
            flowHandle.progress.subscribe { println(">> $it") }
            val result = flowHandle.returnValue.getOrThrow()
            return Response
                    .status(Response.Status.OK)
                    .entity(SingleIdentitySuccessResponseMessage(
                            externalId = message.externalId!!,
                            transactionId = result.id.toString()
                    ))
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
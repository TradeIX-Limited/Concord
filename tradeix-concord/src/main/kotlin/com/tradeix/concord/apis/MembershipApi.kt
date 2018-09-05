package com.tradeix.concord.apis

import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.messages.webapi.FailedResponseMessage
import com.tradeix.concord.messages.webapi.FailedValidationResponseMessage
import com.tradeix.concord.messages.webapi.SingleIdentitySuccessResponseMessage
import com.tradeix.concord.messages.webapi.membership.MembershipConfirmationRequestMessage
import net.corda.businessnetworks.membership.bno.ActivateMembershipFlow
import net.corda.businessnetworks.membership.bno.RevokeMembershipFlow
import net.corda.businessnetworks.membership.member.RequestMembershipFlow
import net.corda.businessnetworks.membership.states.Membership
import net.corda.businessnetworks.membership.states.MembershipMetadata
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.messaging.vaultQueryBy
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.utilities.getOrThrow
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("membership")
class MembershipApi(val services: CordaRPCOps) {

    @POST
    @Path("request")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun RequestMembership(): Response {
        try {
            val flowHandle = services.startTrackedFlow(::RequestMembershipFlow, MembershipMetadata("DEFAULT"))
            flowHandle.progress.subscribe { println(">> $it") }
            val result = flowHandle.returnValue.getOrThrow()
            return Response
                    .status(Response.Status.CREATED)
                    .entity(SingleIdentitySuccessResponseMessage(
                            externalId = result.tx.outputsOfType<Membership.State>().single().linearId.toString(),
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
    @Path("activate")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun ActivateMembership(message: MembershipConfirmationRequestMessage): Response {
        try {
            val criteria = QueryCriteria.LinearStateQueryCriteria(
                    externalId = listOf(message.externalId!!),
                    status = Vault.StateStatus.UNCONSUMED)
            services.vaultQueryBy<Membership.State>(criteria = criteria).states.forEach {
                it.state.data
            }
            val member = services.vaultQueryBy<Membership.State>(criteria = criteria).states.single()
            if (member.state.data.isPending()) {
                val flowHandle = services.startTrackedFlow(::ActivateMembershipFlow, member)
                flowHandle.progress.subscribe { println(">> $it") }
                val result = flowHandle.returnValue.getOrThrow()
                return Response
                        .status(Response.Status.CREATED)
                        .entity(SingleIdentitySuccessResponseMessage(
                                externalId = message.externalId,
                                transactionId = result.id.toString()))
                        .build()
            } else {
                throw Throwable()
            }
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
    @Path("revoke")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun RevokeMembership(message: MembershipConfirmationRequestMessage): Response {
        try {
            val criteria = QueryCriteria.LinearStateQueryCriteria(
                    externalId = listOf(message.externalId!!),
                    status = Vault.StateStatus.UNCONSUMED)
            val member = services.vaultQueryBy<Membership.State>(criteria = criteria).states.single()
            if (member.state.data.isPending()) {
                val flowHandle = services.startTrackedFlow(::RevokeMembershipFlow, member)
                flowHandle.progress.subscribe { println(">> $it") }
                val result = flowHandle.returnValue.getOrThrow()
                return Response
                        .status(Response.Status.CREATED)
                        .entity(SingleIdentitySuccessResponseMessage(
                                externalId = message.externalId,
                                transactionId = result.id.toString()))
                        .build()
            } else {
                throw Throwable()
            }
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

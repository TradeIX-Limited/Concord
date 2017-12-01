package com.tradeix.concord.apis

import com.tradeix.concord.messages.webapi.FailedResponseMessage
import com.tradeix.concord.messages.webapi.nodeinfo.AllNodesResponseMessage
import com.tradeix.concord.messages.webapi.nodeinfo.LocalNodeResponseMessage
import com.tradeix.concord.messages.webapi.nodeinfo.PeerNodesResponseMessage
import net.corda.core.messaging.CordaRPCOps
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("nodes")
class NodeInfoApi(val services: CordaRPCOps) {

    companion object {
        private val KNOWN_NETWORK_NAMES = listOf("R3Net", "Network Map Service")
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllNodes(): Response {
        return try {
            Response
                    .status(Response.Status.OK)
                    .entity(AllNodesResponseMessage(services
                            .networkMapSnapshot()
                            .map { it.legalIdentities.first().name }))
                    .build()
        } catch (ex: Throwable) {
            Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(FailedResponseMessage(ex.message ?: "Unknown server error"))
                    .build()
        }
    }

    @GET
    @Path("peers")
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllPeers(): Response {
        return try {
            Response
                    .status(Response.Status.OK)
                    .entity(PeerNodesResponseMessage(services
                            .networkMapSnapshot()
                            .map { it.legalIdentities.first().name }
                            .filter { it != services.nodeInfo().legalIdentities.first().name }
                            .filter { !KNOWN_NETWORK_NAMES.contains(it.organisation) }))
                    .build()
        } catch (ex: Throwable) {
            Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(FailedResponseMessage(ex.message ?: "Unknown server error"))
                    .build()
        }
    }

    @GET
    @Path("local")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLocalNode(): Response {
        return try {
            Response
                    .status(Response.Status.OK)
                    .entity(LocalNodeResponseMessage(services.nodeInfo().legalIdentities.first().name))
                    .build()
        } catch (ex: Throwable) {
            Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(FailedResponseMessage(ex.message ?: "Unknown server error"))
                    .build()
        }
    }
}
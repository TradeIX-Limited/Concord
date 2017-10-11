package com.tradeix.concord.apis

import net.corda.core.identity.CordaX500Name
import net.corda.core.messaging.CordaRPCOps
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("nodes")
class NodeInfoApi(val services: CordaRPCOps) {

    private val NOTARY_NAME = "Controller"
    private val NETWORK_MAP_NAME = "Network Map Service"

    private val myLegalName: CordaX500Name = services.nodeInfo().legalIdentities.first().name

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllNodes(): Response = Response
            .status(Response.Status.OK)
            .entity(services
                    .networkMapSnapshot()
                    .map { it.legalIdentities.first().name }
                    .filter { it != myLegalName && it.organisation != NOTARY_NAME && it.organisation != NETWORK_MAP_NAME })
            .build()


    @GET
    @Path("local")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLocalNode(): Response = Response
            .status(Response.Status.OK)
            .entity(myLegalName)
            .build()

}
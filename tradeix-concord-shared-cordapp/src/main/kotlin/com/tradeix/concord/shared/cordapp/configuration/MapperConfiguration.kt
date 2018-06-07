package com.tradeix.concord.shared.cordapp.configuration

import com.tradeix.concord.shared.cordapp.mapping.registerInvoiceMappers
import com.tradeix.concord.shared.cordapp.mapping.registerPurchaseOrderMappers
import com.tradeix.concord.shared.mapper.Mapper
import net.corda.core.node.AppServiceHub
import net.corda.core.node.services.CordaService
import net.corda.core.serialization.SingletonSerializeAsToken

@CordaService
class MapperConfiguration(val services: AppServiceHub) : SingletonSerializeAsToken() {
    init {
        Mapper.registerInvoiceMappers()
        Mapper.registerPurchaseOrderMappers()
        println("Mapper configuration registration complete")
    }
}
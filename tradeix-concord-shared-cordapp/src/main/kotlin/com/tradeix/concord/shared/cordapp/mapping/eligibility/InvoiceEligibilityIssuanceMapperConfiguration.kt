//package com.tradeix.concord.shared.cordapp.mapping.eligibility
//
//import com.tradeix.concord.shared.domain.states.InvoiceEligibilityState
//import com.tradeix.concord.shared.mapper.ServiceHubMapperConfiguration
//import com.tradeix.concord.shared.messages.InvoiceEligibilityMessage
//import com.tradeix.concord.shared.services.IdentityService
//import com.tradeix.concord.shared.services.VaultService
//import net.corda.core.node.ServiceHub
//
//class InvoiceEligibilityIssuanceMapperConfiguration
//    : ServiceHubMapperConfiguration<InvoiceEligibilityMessage, InvoiceEligibilityState>() {
//
//    override fun map(source: InvoiceEligibilityMessage, serviceHub: ServiceHub): InvoiceEligibilityState {
//
//        val vaultService = VaultService.fromServiceHub<InvoiceEligibilityState>(serviceHub)
//        val identityService = IdentityService(serviceHub)
//
//        val state = vaultService
//                .findByExternalId(source.)
//
//    }
//}
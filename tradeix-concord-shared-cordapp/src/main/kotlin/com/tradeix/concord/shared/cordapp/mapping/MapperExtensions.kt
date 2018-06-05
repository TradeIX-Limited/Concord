package com.tradeix.concord.shared.cordapp.mapping

import com.tradeix.concord.shared.cordapp.mapping.purchaseorders.PurchaseOrderAmendmentMapperConfiguration
import com.tradeix.concord.shared.cordapp.mapping.purchaseorders.PurchaseOrderIssuanceMapperConfiguration
import com.tradeix.concord.shared.data.VaultRepository
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.fromValueAndCurrency
import com.tradeix.concord.shared.extensions.getPartyFromLegalNameOrMe
import com.tradeix.concord.shared.extensions.getPartyFromLegalNameOrNull
import com.tradeix.concord.shared.extensions.tryParse
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.mapper.MapperConfiguration
import com.tradeix.concord.shared.mapper.ServiceHubMapperConfiguration
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault

fun Mapper.registerInvoiceMappers() {

    this.addConfiguration("issuance", object : ServiceHubMapperConfiguration<InvoiceRequestMessage, InvoiceState>() {
        override fun map(source: InvoiceRequestMessage, serviceHub: ServiceHub): InvoiceState {
            val repository = VaultRepository.fromServiceHub<InvoiceState>(serviceHub)

            val state = repository
                    .findByExternalId(source.externalId!!, status = Vault.StateStatus.UNCONSUMED)
                    .singleOrNull()

            if (state != null) {
                throw FlowException("An InvoiceState with '${source.externalId}' already exists.")
            }

            val buyer = serviceHub.networkMapCache.getPartyFromLegalNameOrNull(
                    CordaX500Name.tryParse(source.buyer)
            )

            val supplier = serviceHub.networkMapCache.getPartyFromLegalNameOrMe(
                    serviceHub,
                    CordaX500Name.tryParse(source.supplier)
            )

            return InvoiceState(
                    linearId = UniqueIdentifier(source.externalId!!),
                    owner = supplier,
                    buyer = buyer,
                    supplier = supplier,
                    invoiceNumber = source.invoiceNumber!!,
                    reference = source.reference!!,
                    dueDate = source.dueDate!!,
                    amount = Amount.fromValueAndCurrency(source.amount!!, source.currency!!),
                    totalOutstanding = Amount.fromValueAndCurrency(source.totalOutstanding!!, source.currency!!),
                    settlementDate = source.settlementDate!!,
                    invoiceDate = source.invoiceDate!!,
                    invoicePayments = Amount.fromValueAndCurrency(source.invoicePayments!!, source.currency!!),
                    invoiceDilutions = Amount.fromValueAndCurrency(source.invoiceDilutions!!, source.currency!!),
                    originationNetwork = source.originationNetwork!!,
                    siteId = source.siteId!!
            )
        }
    })

    this.addConfiguration("amendment", object : ServiceHubMapperConfiguration<InvoiceRequestMessage, InvoiceState>() {
        override fun map(source: InvoiceRequestMessage, serviceHub: ServiceHub): InvoiceState {
            val repository = VaultRepository.fromServiceHub<InvoiceState>(serviceHub)

            val inputState = repository
                    .findByExternalId(source.externalId!!, status = Vault.StateStatus.UNCONSUMED)
                    .singleOrNull()

            if (inputState == null) {
                throw FlowException("InvoiceState with externalId '${source.externalId}' does not exist.")
            } else {

                val buyer = serviceHub.networkMapCache.getPartyFromLegalNameOrNull(
                        CordaX500Name.tryParse(source.buyer)
                )

                val supplier = serviceHub.networkMapCache.getPartyFromLegalNameOrMe(
                        serviceHub,
                        CordaX500Name.tryParse(source.supplier)
                )

                return inputState.state.data.copy(
                        owner = supplier,
                        buyer = buyer,
                        supplier = supplier,
                        invoiceNumber = source.invoiceNumber!!,
                        reference = source.reference!!,
                        dueDate = source.dueDate!!,
                        amount = Amount.fromValueAndCurrency(source.amount!!, source.currency!!),
                        totalOutstanding = Amount.fromValueAndCurrency(source.totalOutstanding!!, source.currency!!),
                        settlementDate = source.settlementDate!!,
                        invoiceDate = source.invoiceDate!!,
                        invoicePayments = Amount.fromValueAndCurrency(source.invoicePayments!!, source.currency!!),
                        invoiceDilutions = Amount.fromValueAndCurrency(source.invoiceDilutions!!, source.currency!!),
                        originationNetwork = source.originationNetwork!!,
                        siteId = source.siteId!!
                )
            }
        }
    })

    this.addConfiguration("response", object : MapperConfiguration<InvoiceState, InvoiceRequestMessage>() {
        override fun map(source: InvoiceState): InvoiceRequestMessage {
            return InvoiceRequestMessage(
                    externalId = source.linearId.externalId,
                    buyer = source.buyer?.nameOrNull()?.toString(),
                    supplier = source.supplier.nameOrNull()?.toString(),
                    invoiceNumber = source.invoiceNumber,
                    reference = source.reference,
                    dueDate = source.dueDate,
                    amount = source.amount.toDecimal(),
                    totalOutstanding = source.totalOutstanding.toDecimal(),
                    settlementDate = source.settlementDate,
                    invoiceDate = source.invoiceDate,
                    invoicePayments = source.invoicePayments.toDecimal(),
                    invoiceDilutions = source.invoiceDilutions.toDecimal(),
                    originationNetwork = source.originationNetwork,
                    currency = source.amount.token.currencyCode,
                    siteId = source.siteId
            )
        }
    })
}

fun Mapper.registerPurchaseOrderMappers() {
    this.addConfiguration("issuance", PurchaseOrderIssuanceMapperConfiguration())
    this.addConfiguration("amendment", PurchaseOrderAmendmentMapperConfiguration())
}
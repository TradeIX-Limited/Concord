package com.tradeix.concord.cordapp.configuration.mapping

import com.tradeix.concord.cordapp.configuration.mapping.invoices
        .InvoiceAmendmentRequestMessageToInvoiceStateMapperConfiguration

import com.tradeix.concord.cordapp.configuration.mapping.invoices
        .InvoiceIssuanceRequestMessageToInvoiceStateMapperConfiguration

import com.tradeix.concord.cordapp.configuration.mapping.purchaseorders
        .PurchaseOrderAmendmentRequestMessageToPurchaseOrderStateMapperConfiguration

import com.tradeix.concord.cordapp.configuration.mapping.purchaseorders
        .PurchaseOrderIssuanceRequestMessageToPurchaseOrderStateMapperConfiguration

import com.tradeix.concord.shared.mapper.Mapper

fun Mapper.registerInvoiceMappers() {
    this.addConfiguration(InvoiceIssuanceRequestMessageToInvoiceStateMapperConfiguration())
    this.addConfiguration(InvoiceAmendmentRequestMessageToInvoiceStateMapperConfiguration())
}

fun Mapper.registerPurchaseOrderMappers() {
    this.addConfiguration(PurchaseOrderIssuanceRequestMessageToPurchaseOrderStateMapperConfiguration())
    this.addConfiguration(PurchaseOrderAmendmentRequestMessageToPurchaseOrderStateMapperConfiguration())
}
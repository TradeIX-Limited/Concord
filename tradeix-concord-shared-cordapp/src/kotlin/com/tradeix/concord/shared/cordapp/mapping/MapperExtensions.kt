package com.tradeix.concord.shared.cordapp.mapping

import com.tradeix.concord.shared.cordapp.mapping.invoices
        .InvoiceAmendmentMapperConfiguration

import com.tradeix.concord.shared.cordapp.mapping.invoices
        .InvoiceIssuanceMapperConfiguration

import com.tradeix.concord.shared.cordapp.mapping.purchaseorders
        .PurchaseOrderAmendmentMapperConfiguration

import com.tradeix.concord.shared.cordapp.mapping.purchaseorders
        .PurchaseOrderIssuanceMapperConfiguration

import com.tradeix.concord.shared.mapper.Mapper

fun Mapper.registerInvoiceMappers() {
    this.addConfiguration("issuance", InvoiceIssuanceMapperConfiguration())
    this.addConfiguration("amendment", InvoiceAmendmentMapperConfiguration())
}

fun Mapper.registerPurchaseOrderMappers() {
    this.addConfiguration("issuance", PurchaseOrderIssuanceMapperConfiguration())
    this.addConfiguration("amendment", PurchaseOrderAmendmentMapperConfiguration())
}
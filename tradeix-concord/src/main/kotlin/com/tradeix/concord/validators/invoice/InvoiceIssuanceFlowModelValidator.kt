package com.tradeix.concord.validators.invoice

import com.tradeix.concord.extensions.ArrayListExtensions.addWhen
import com.tradeix.concord.flowmodels.invoice.InvoiceIssuanceFlowModel
import com.tradeix.concord.validators.Validator
import java.math.BigDecimal

class InvoiceIssuanceFlowModelValidator(message: InvoiceIssuanceFlowModel)
    : Validator<InvoiceIssuanceFlowModel>(message) {
    companion object {
        // rule messages
        private val EX_EXTERNAL_ID_REQUIRED = "Field 'externalId' is required."
        private val EX_BUYER_REQUIRED = "Field 'buyer' is required."
        private val EX_SUPPLIER_REQUIRED = "Field 'supplier' is required."
        private val EX_INVOICENUMBER_REQUIRED = "Field 'invoiceNumber' is required."
        private val EX_INVOICETYPE_REQUIRED = "Field 'invoiceType' is required."
        private val EX_DUEDATE_REQUIRED = "Field 'dueDate' is required."
        private val EX_AMOUNT_REQUIRED = "Field 'amount' is required."
        private val EX_TOTALOUTSTANDING_REQUIRED = "Field 'totalOutstanding' is required."
        private val EX_CREATED_REQUIRED = "Field 'created' is required."
        private val EX_UPDATED_REQUIRED = "Field 'updated' is required."
        private val EX_EXPECTED_SETTLEMENT_REQUIRED = "Field 'expectedSettlementDate' is required."
        private val EX_INVOICEDATE_SHIPMENT = "Field 'invoiceDate' is required."
        private val EX_STATUS_REQUIRED = "Field 'status' is required."
        private val EX_ELIGIBLE_VALUE_REQUIRED = "Field 'eligibleValue' is required."
        private val EX_INVOICE_PURCHASEVALUE_REQUIRED = "Field 'invoicePurchaseValue' is required."
        private val EX_INVOICE_PAYMENTS_REQUIRED = "Field 'invoicePayments' is required."
        private val EX_INVOICE_DILUTIONS_REQUIRED = "Field 'invoiceDilutions' is required."
        private val EX_ORIGINATION_NETWORK_REQUIRED = "Field 'originationNetwork' is required."
        private val EX_CURRENCY_REQUIRED = "Field 'currency' is required."
    }
    override fun validate() {
        errors.addWhen(message.externalId.isNullOrBlank(), EX_EXTERNAL_ID_REQUIRED)
        errors.addWhen(message.buyer == null, EX_BUYER_REQUIRED)
        errors.addWhen(message.supplier == null, EX_SUPPLIER_REQUIRED)
        errors.addWhen(message.invoiceNumber.isNullOrBlank(), EX_INVOICENUMBER_REQUIRED)
        errors.addWhen(message.invoiceType.isNullOrBlank(), EX_INVOICETYPE_REQUIRED)
        errors.addWhen(message.dueDate == null, EX_DUEDATE_REQUIRED)
        errors.addWhen((message.amount == null) || (message.amount <= BigDecimal.ZERO), EX_AMOUNT_REQUIRED)
        errors.addWhen((message.totalOutstanding==null) || (message.totalOutstanding < BigDecimal.ZERO), EX_TOTALOUTSTANDING_REQUIRED)
        errors.addWhen(message.created == null, EX_CREATED_REQUIRED)
        errors.addWhen(message.updated == null, EX_UPDATED_REQUIRED)
        errors.addWhen(message.expectedSettlementDate == null, EX_EXPECTED_SETTLEMENT_REQUIRED)
        errors.addWhen(message.invoiceDate == null, EX_INVOICEDATE_SHIPMENT)
        errors.addWhen(message.status.isNullOrBlank(), EX_STATUS_REQUIRED)
        errors.addWhen((message.eligibleValue==null) || (message.eligibleValue < BigDecimal.ZERO), EX_ELIGIBLE_VALUE_REQUIRED)
        errors.addWhen((message.invoicePurchaseValue == null) || (message.invoicePurchaseValue < BigDecimal.ZERO), EX_INVOICE_PURCHASEVALUE_REQUIRED)
        errors.addWhen((message.invoicePayments == null) || (message.invoicePayments < BigDecimal.ZERO), EX_INVOICE_PAYMENTS_REQUIRED)
        errors.addWhen((message.invoiceDilutions == null) || (message.invoiceDilutions < BigDecimal.ZERO), EX_INVOICE_DILUTIONS_REQUIRED)
        errors.addWhen(message.originationNetwork.isNullOrBlank(), EX_ORIGINATION_NETWORK_REQUIRED)
        errors.addWhen(message.currency.isNullOrBlank(), EX_CURRENCY_REQUIRED)
        errors.addWhen(message.created == null, EX_CREATED_REQUIRED)
    }
}
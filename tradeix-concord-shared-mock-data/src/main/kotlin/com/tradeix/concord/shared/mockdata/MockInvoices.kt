package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.messages.*
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_FUTURE_1
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_FUTURE_2
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_1
import net.corda.core.identity.CordaX500Name
import java.math.BigDecimal

object MockInvoices {

    fun createMockInvoices(
            count: Int,
            buyer: CordaX500Name?,
            supplier: CordaX500Name?,
            observers: Iterable<CordaX500Name>?): InvoiceTransactionRequestMessage {

        return InvoiceTransactionRequestMessage(
                assets = (1..count).toList().map {
                    INVOICE_REQUEST_MESSAGE.copy(
                            externalId = "INVOICE_$it",
                            buyer = buyer?.toString(),
                            supplier = supplier?.toString(),
                            invoiceNumber = "$it",
                            reference = "REF_INVOICE_$it"
                    )
                },
                observers = observers?.map { it.toString() } ?: emptyList(),
                attachments = emptyList()
        )
    }

    fun createMockInvoiceAmendments(
            count: Int,
            buyer: CordaX500Name?,
            supplier: CordaX500Name?,
            observers: Iterable<CordaX500Name>?): InvoiceTransactionRequestMessage {

        val message =  createMockInvoices(count, buyer, supplier, observers)

        return InvoiceTransactionRequestMessage(
                assets = message.assets.map { it.copy(reference = it.reference + "_AMENDED") },
                observers = message.observers,
                attachments = message.attachments
        )
    }

    fun createMockInvoiceCancellations(
            count: Int,
            observers: Iterable<CordaX500Name>?): CancellationTransactionRequestMessage {
        return CancellationTransactionRequestMessage(
                assets = (1..count).toList().map { CancellationRequestMessage("INVOICE_$it") },
                observers = observers?.map { it.toString() } ?: emptyList()
        )
    }

    fun createMockInvoiceOwnershipChanges(count: Int, owner: CordaX500Name): OwnershipTransactionRequestMessage {
        return OwnershipTransactionRequestMessage(
                assets = (1..count).toList().map { OwnershipRequestMessage("INVOICE_$it", owner.toString()) }
        )
    }

    val INVOICE_REQUEST_MESSAGE = InvoiceRequestMessage(
            externalId = "INVOICE_001",
            buyer = BUYER_1_NAME.toString(),
            supplier = SUPPLIER_1_NAME.toString(),
            invoiceNumber = "001",
            reference = "REF_INVOICE_001",
            dueDate = LOCAL_DATE_TIME_FUTURE_1,
            amount = BigDecimal.valueOf(123.45),
            totalOutstanding = BigDecimal.valueOf(123.45),
            settlementDate = LOCAL_DATE_TIME_FUTURE_1,
            invoiceDate = LOCAL_DATE_TIME_PAST_1,
            invoicePayments = BigDecimal.ZERO,
            invoiceDilutions = BigDecimal.ZERO,
            originationNetwork = "Mock Corda Network",
            currency = "GBP",
            siteId = "MOCK_CORDA_NETWORK",
            buyerCompanyName = "Buyer 1",
            tradePaymentDate = LOCAL_DATE_TIME_FUTURE_2,
            tradeDate = LOCAL_DATE_TIME_FUTURE_1
    )

    val INVOICE_CHANGE_OWNER_REQUEST_MESSAGE = OwnershipRequestMessage(
            externalId = "INVOICE_001",
            owner = FUNDER_1_NAME.toString()
    )

    val INVOICE_CANCELLATION_REQUEST_MESSAGE = CancellationRequestMessage(
            externalId = "INVOICE_001"
    )
}
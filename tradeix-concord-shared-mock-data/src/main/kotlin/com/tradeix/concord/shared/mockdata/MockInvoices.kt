package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.messages.CancellationRequestMessage
import com.tradeix.concord.shared.messages.InvoiceTransactionRequestMessage
import com.tradeix.concord.shared.messages.OwnershipRequestMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceMessage
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_FUTURE_1
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

    val INVOICE_REQUEST_MESSAGE = InvoiceMessage(
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
            siteId = "MOCK_CORDA_NETWORK"
    )

    val INVOICE_CHANGE_OWNER_REQUEST_MESSAGE = OwnershipRequestMessage(
            externalId = "INVOICE_001",
            owner = FUNDER_1_NAME.toString()
    )

    val INVOICE_CANCELLATION_REQUEST_MESSAGE = CancellationRequestMessage(
            externalId = "INVOICE_001"
    )
}
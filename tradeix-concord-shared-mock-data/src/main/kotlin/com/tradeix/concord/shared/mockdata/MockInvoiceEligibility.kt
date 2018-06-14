package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.messages.InvoiceEligibilityRequestMessage
import com.tradeix.concord.shared.messages.InvoiceEligibilityTransactionRequestMessage
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import net.corda.core.identity.CordaX500Name

object MockInvoiceEligibility {

    fun createMockInvoiceEligibility(
            count: Int,
            supplier: CordaX500Name): InvoiceEligibilityTransactionRequestMessage {

        return InvoiceEligibilityTransactionRequestMessage(
                assets = (1..count).toList().map {
                    INVOICE_ELIGIBILITY_REQUEST_MESSAGE.copy(
                            invoiceId = "INVOICE_$it",
                            supplier = supplier.toString()
                    )
                }
        )
    }

    val INVOICE_ELIGIBILITY_REQUEST_MESSAGE = InvoiceEligibilityRequestMessage(
            invoiceId = "INVOICE_001",
            supplier = SUPPLIER_1_NAME.toString(),
            eligible = true
    )
}
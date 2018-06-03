package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import java.time.LocalDateTime
import java.util.*

data class InvoiceState(
        override val linearId: UniqueIdentifier,
        override val owner: AbstractParty,
        val buyer: AbstractParty?,
        val supplier: AbstractParty,
        val invoiceVersion: String,
        val invoiceVersionDate: LocalDateTime,
        val tixInvoiceVersion: Int,                             // TODO : Necessary?
        val invoiceNumber: String,
        val invoiceType: String,                                // TODO : Make enum?
        val reference: String,
        val dueDate: LocalDateTime,
        val offerId: Int?,                                      // TODO : Necessary?
        val amount: Amount<Currency>,
        val totalOutstanding: Amount<Currency>,
        val created: LocalDateTime,
        val updated: LocalDateTime,
        val expectedSettlementDate: LocalDateTime,
        val settlementDate: LocalDateTime?,
        val mandatoryReconciliationDate: LocalDateTime?,
        val invoiceDate: LocalDateTime,
        val status: String,                                     // TODO : Make enum?
        val rejectionReason: String?,
        val eligibleValue: Amount<Currency>,
        val invoicePurchaseValue: Amount<Currency>,
        val tradeDate: LocalDateTime?,
        val tradePaymentDate: LocalDateTime?,
        val invoicePayments: Amount<Currency>,
        val invoiceDilutions: Amount<Currency>,
        val cancelled: Boolean,
        val closeDate: LocalDateTime?,
        val originationNetwork: String,                         // TODO : Necessary?
        val currency: Currency,                                 // TODO : Necessary?
        val siteId: String,                                     // TODO : Necessary?
        val purchaseOrderNumber: String,                        // TODO : Necessary?
        val purchaseOrderId: String,                            // TODO : Make UniqueIdentifier?
        val composerProgramId: Int?                             // TODO : Necessary?
) : LinearState, OwnableState {

    override val participants: List<AbstractParty> get() = listOfNotNull(owner, buyer, supplier)

    override fun withNewOwner(newOwner: AbstractParty): CommandAndState {
        return CommandAndState(InvoiceContract.Commands.ChangeOwner(), this.copy(owner = newOwner))
    }
}
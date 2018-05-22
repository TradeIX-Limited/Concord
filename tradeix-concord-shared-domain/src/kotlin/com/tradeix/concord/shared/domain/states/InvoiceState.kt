package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import java.time.Instant
import java.util.*

data class InvoiceState(
        override val linearId: UniqueIdentifier,
        override val owner: AbstractParty,
        val buyer: AbstractParty?,
        val supplier: AbstractParty,
        val conductor: AbstractParty,
        val invoiceVersion: String,
        val invoiceVersionDate: Instant,
        val tixInvoiceVersion: Int,                             // TODO : Necessary?
        val invoiceNumber: String,
        val invoiceType: String,                                // TODO : Make enum?
        val reference: String,
        val dueDate: Instant,
        val offerId: Int?,                                      // TODO : Necessary?
        val amount: Amount<Currency>,
        val totalOutstanding: Amount<Currency>,
        val created: Instant,
        val updated: Instant,
        val expectedSettlementDate: Instant,
        val settlementDate: Instant?,
        val mandatoryReconciliationDate: Instant?,
        val invoiceDate: Instant,
        val status: String,                                     // TODO : Make enum?
        val rejectionReason: String?,
        val eligibleValue: Amount<Currency>,
        val invoicePurchaseValue: Amount<Currency>,
        val tradeDate: Instant?,
        val tradePaymentDate: Instant?,
        val invoicePayments: Amount<Currency>,
        val invoiceDilutions: Amount<Currency>,
        val cancelled: Boolean,
        val closeDate: Instant?,
        val originationNetwork: String,                         // TODO : Necessary?
        val currency: Currency,                                 // TODO : Necessary?
        val siteId: String,                                     // TODO : Necessary?
        val purchaseOrderNumber: String,                        // TODO : Necessary?
        val purchaseOrderId: String,                            // TODO : Make UniqueIdentifier?
        val composerProgramId: Int?                             // TODO : Necessary?
) : LinearState, OwnableState {

    override val participants: List<AbstractParty>
        get() {
            return if (buyer != null) {
                listOf(owner, buyer, supplier, conductor)
            } else {
                listOf(owner, supplier, conductor)
            }
        }

    override fun withNewOwner(newOwner: AbstractParty): CommandAndState {
        return CommandAndState(InvoiceContract.Commands.ChangeOwner(), this.copy(owner = newOwner))
    }
}
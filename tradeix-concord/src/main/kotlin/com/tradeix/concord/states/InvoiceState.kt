package com.tradeix.concord.states

import com.tradeix.concord.interfaces.OwnerState
import com.tradeix.concord.schemas.InvoiceSchemaV1
import net.corda.core.contracts.Amount
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import java.time.Instant
import java.util.*

data class InvoiceState(
        override val linearId: UniqueIdentifier,
        override val owner: AbstractParty,
        val buyer: AbstractParty,
        val supplier: AbstractParty,
        val funder: AbstractParty,
        val conductor: AbstractParty,
        val invoiceVersion: String,
        val invoiceVersionDate: Instant,
        val tixInvoiceVersion: Int,
        val invoiceNumber: String,
        val invoiceType: String,
        val reference: String,
        val dueDate: Instant,
        val offerId: Int?,
        val amount: Amount<Currency>,
        val totalOutstanding: Amount<Currency>,
        val created: Instant,
        val updated: Instant,
        val expectedSettlementDate: Instant,
        val settlementDate: Instant?,
        val mandatoryReconciliationDate: Instant?,
        val invoiceDate: Instant,
        val status: String,
        val rejectionReason: String,
        val eligibleValue: Amount<Currency>,
        val invoicePurchaseValue: Amount<Currency>,
        val tradeDate: Instant?,
        val tradePaymentDate: Instant?,
        val invoicePayments: Amount<Currency>,
        val invoiceDilutions: Amount<Currency>,
        val cancelled: Boolean,
        val closeDate: Instant?,
        val originationNetwork: String,
        val hash: String,
        val currency: Currency,
        val siteId: String,
        val purchaseOrderNumber: String,
        val purchaseOrderId: String,
        val composerProgramId: Int
) : LinearState, OwnerState, QueryableState {
    override val participants: List<AbstractParty> get() = listOf(owner, buyer, supplier, funder, conductor)

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when(schema) {
            is InvoiceSchemaV1 -> InvoiceSchemaV1.PersistentInvoiceSchemaV1(
                    linearId = linearId.id,
                    externalId = linearId.externalId.toString(),
                    owner = owner,
                    buyer = buyer,
                    supplier = supplier,
                    funder = funder,
                    conductor = conductor,
                    invoiceVersion = invoiceVersion,
                    invoiceVersionDate = invoiceVersionDate,
                    tixInvoiceVersion = tixInvoiceVersion,
                    invoiceNumber = invoiceNumber,
                    invoiceType = invoiceType,
                    reference = reference,
                    dueDate = dueDate,
                    offerId = offerId,
                    amount = amount.toDecimal(),
                    totalOutstanding = totalOutstanding.toDecimal(),
                    created = created,
                    updated = updated,
                    expectedSettlementDate = expectedSettlementDate,
                    settlementDate = settlementDate,
                    mandatoryReconciliationDate = mandatoryReconciliationDate,
                    invoiceDate = invoiceDate,
                    status = status,
                    rejectionReason = rejectionReason,
                    eligibleValue = eligibleValue.toDecimal(),
                    invoicePurchaseValue = invoicePurchaseValue.toDecimal(),
                    tradeDate = tradeDate,
                    tradePaymentDate = tradePaymentDate,
                    invoicePayments = invoicePayments.toDecimal(),
                    invoiceDilutions = invoiceDilutions.toDecimal(),
                    cancelled = cancelled,
                    closeDate = closeDate,
                    originationNetwork = originationNetwork,
                    hash = hash,
                    currency = currency.toString(),
                    siteId = siteId,
                    purchaseOrderNumber = purchaseOrderNumber,
                    purchaseOrderId = purchaseOrderId,
                    composerProgramId = composerProgramId
            )
            else -> throw IllegalArgumentException("Unrecognised schemas $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(InvoiceSchemaV1)
}
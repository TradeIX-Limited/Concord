package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import com.tradeix.concord.shared.domain.mapping.FundingResponseSchemaV1Mapper
import com.tradeix.concord.shared.domain.schemas.FundingResponseSchemaV1
import com.tradeix.concord.shared.models.BankAccount
import net.corda.core.contracts.Amount
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class FundingResponseState(
        override val linearId: UniqueIdentifier,
        val fundingRequestLinearId: UniqueIdentifier,
        val invoiceLinearIds: Collection<UniqueIdentifier>,
        val supplier: AbstractParty,
        val funder: AbstractParty,
        val purchaseValue: Amount<Currency>,
        val status: FundingResponseStatus,
        val advanceInvoiceValue: Amount<Currency>,
        val discountValue: Amount<Currency>,
        val baseRate: BigDecimal,
        val bankAccount: BankAccount?,
        val transactionFee: BigDecimal,
        val submitted: LocalDateTime
) : LinearState, QueryableState {

    override val participants: List<AbstractParty> get() = listOf(supplier, funder)

    override fun supportedSchemas(): Iterable<MappedSchema> {
        return listOf(FundingResponseSchemaV1)
    }

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is FundingResponseSchemaV1 -> FundingResponseSchemaV1Mapper().map(this)
            else -> throw IllegalArgumentException("Unrecognised schemas $schema")
        }
    }

    fun accept(bankAccount: BankAccount) = copy(status = FundingResponseStatus.ACCEPTED, bankAccount = bankAccount, submitted = LocalDateTime.now())

    fun reject() = copy(status = FundingResponseStatus.REJECTED, submitted = LocalDateTime.now())
}
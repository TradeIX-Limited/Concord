package com.tradeix.concord.cordapp.supplier.mappers.invoices

import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceResponseMessage
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.mapper.Mapper
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.hash

class InvoiceIssuanceConfirmationMapper
    : Mapper<StateAndRef<InvoiceState>, InvoiceResponseMessage>() {

    override fun map(source: StateAndRef<InvoiceState>): InvoiceResponseMessage {
        return InvoiceResponseMessage(
                externalId = source.state.data.linearId.externalId!!,
                transactionId = source.ref.txhash.toString(),
                stateHash = source.state.data.hash().toString()
        )
    }
}
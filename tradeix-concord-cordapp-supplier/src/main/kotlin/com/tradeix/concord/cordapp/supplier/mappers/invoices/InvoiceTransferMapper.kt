package com.tradeix.concord.cordapp.supplier.mappers.invoices

import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransferRequestMessage
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.tryParse
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault
import java.time.LocalDateTime

class InvoiceTransferMapper(private val serviceHub: ServiceHub)
    : Mapper<InvoiceTransferRequestMessage, InputAndOutput<InvoiceState>>() {

    override fun map(source: InvoiceTransferRequestMessage): InputAndOutput<InvoiceState> {

        val vaultService = VaultService.fromServiceHub<InvoiceState>(serviceHub)
        val identityService = IdentityService(serviceHub)

        val inputState = vaultService
                .findByExternalId(source.externalId!!, status = Vault.StateStatus.UNCONSUMED)
                .singleOrNull()

        val count = vaultService.getCount(source.externalId)

        if (inputState == null) {
            throw FlowException("InvoiceState with externalId '${source.externalId}' does not exist.")
        } else {

            val owner = identityService.getPartyFromLegalNameOrThrow(CordaX500Name.tryParse(source.owner))
            val outputState = inputState.state.data.copy(
                    owner = owner,
                    invoiceVersion = "${count + 1}.0",
                    submitted = LocalDateTime.now()
            )

            return InputAndOutput(inputState, outputState)
        }
    }
}
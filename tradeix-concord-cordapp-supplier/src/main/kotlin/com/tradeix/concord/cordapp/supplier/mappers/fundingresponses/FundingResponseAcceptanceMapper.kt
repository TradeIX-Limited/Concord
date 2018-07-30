package com.tradeix.concord.cordapp.supplier.mappers.fundingresponses

import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationRequestMessage
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.models.Address
import com.tradeix.concord.shared.models.BankAccount
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.flows.FlowException
import net.corda.core.node.ServiceHub
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class FundingResponseAcceptanceMapper(private val serviceHub: ServiceHub)
    : Mapper<FundingResponseConfirmationRequestMessage, InputAndOutput<FundingResponseState>>() {

    companion object {
        private val logger: Logger = loggerFor<FundingResponseAcceptanceMapper>()
    }

    override fun map(source: FundingResponseConfirmationRequestMessage): InputAndOutput<FundingResponseState> {

        val vaultService = VaultService.fromServiceHub<FundingResponseState>(serviceHub)

        val inputState = vaultService
                .findByExternalId(source.externalId!!)
                .singleOrNull()

        if (inputState == null) {
            val message = "A FundingResponseState with externalId '${source.externalId}' does not exist."
            logger.error(message)
            throw FlowException(message)
        } else {
            val bankAddress = Address(
                    residenceNameOrNumber = source.bankAccount!!.bankAddress?.residenceNameOrNumber,
                    unitNameOrNumber = source.bankAccount.bankAddress?.unitNameOrNumber,
                    street = source.bankAccount.bankAddress?.street,
                    locality = source.bankAccount.bankAddress?.locality,
                    municipality = source.bankAccount.bankAddress?.municipality,
                    province = source.bankAccount.bankAddress?.province,
                    country = source.bankAccount.bankAddress?.country,
                    postalCode = source.bankAccount.bankAddress?.postalCode
            )

            val bankAccount = BankAccount(
                    accountName = source.bankAccount.accountName!!,
                    accountNumber = source.bankAccount.accountNumber,
                    sortCode = source.bankAccount.sortCode,
                    bankIdentifierCode = source.bankAccount.bankIdentifierCode,
                    internationalBankAccountNumber = source.bankAccount.internationalBankAccountNumber,
                    abaNumber = source.bankAccount.abaNumber,
                    bankName = source.bankAccount.bankName,
                    bankAddress = bankAddress
            )

            val outputState = inputState.state.data.accept(bankAccount)
            return InputAndOutput(inputState, outputState)
        }
    }
}
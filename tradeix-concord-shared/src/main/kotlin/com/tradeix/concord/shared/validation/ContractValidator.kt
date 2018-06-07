package com.tradeix.concord.shared.validation

import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

abstract class ContractValidator : Validator() {

    override fun addValidationMessage(validationMessage: String) {
        if(!emulating) {
            throw ValidationException(listOf(validationMessage))
        }
        validationMessages.add(validationMessage)
    }

    override fun getValidationMessages(): Iterable<String> {
        emulating = true
        validateInternal(ValidationContext(this, emulating, null), null, emptyList())
        return validationMessages
    }

    fun validate(transaction: LedgerTransaction, signers: List<PublicKey>) {
        emulating = false
        validateInternal(ValidationContext(this, emulating, null), transaction, signers)
        if (validationMessages.isNotEmpty()) {
            throw ValidationException(validationMessages)
        }
    }

    private fun validateInternal(
            context: ValidationContext,
            transaction: LedgerTransaction?,
            signers: List<PublicKey> = emptyList()) {
        validationMessages.clear()
        validate(ContractValidationBuilder(context, transaction, signers))
    }

    protected abstract fun validate(validationBuilder: ContractValidationBuilder)
}
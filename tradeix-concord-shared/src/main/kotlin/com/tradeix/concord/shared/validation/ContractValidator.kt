package com.tradeix.concord.shared.validation

import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

abstract class ContractValidator : Validator(ValidationBehavior.THROW_EXCEPTION_ON_ERROR) {

    override fun getValidationMessages(): Iterable<String> {
        initializeValidation(false)
        return validationMessages
    }

    fun validate(transaction: LedgerTransaction, signers: List<PublicKey>) {
        initializeValidation(true, transaction, signers)
        if (validationMessages.isNotEmpty()) {
            throw ValidationException(validationMessages)
        }
    }

    protected abstract fun onValidationBuilding(validationBuilder: ContractValidationBuilder, signers: List<PublicKey>)

    private fun initializeValidation(
            validating: Boolean,
            transaction: LedgerTransaction? = null,
            signers: List<PublicKey> = listOf()) {

        validationMessages.clear()
        this.validating = validating
        onValidationBuilding(ContractValidationBuilder(this, transaction), signers)
    }
}
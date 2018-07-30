package com.tradeix.concord.shared.models

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class BankAccount(
        val accountName: String,
        val accountNumber: String?,
        val sortCode: String?,
        val bankIdentifierCode: String?,
        val internationalBankAccountNumber: String?,
        val abaNumber: String?,
        val bankName: String?,
        val bankAddress: Address?
)
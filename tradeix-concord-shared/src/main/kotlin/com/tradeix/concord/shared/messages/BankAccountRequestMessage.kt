package com.tradeix.concord.shared.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class BankAccountRequestMessage(
        val accountName: String? = null,
        val accountNumber: String? = null,
        val sortCode: String? = null,
        val bankIdentifierCode: String? = null,
        val internationalBankAccountNumber: String? = null,
        val abaNumber: String? = null,
        val bankName: String? = null,
        val bankAddress: AddressRequestMessage? = null
)
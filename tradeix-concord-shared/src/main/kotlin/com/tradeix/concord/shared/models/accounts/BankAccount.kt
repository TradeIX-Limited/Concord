package com.tradeix.concord.shared.models.accounts

import java.util.*

data class BankAccount(
        val accountName: String,
        val accountNumber: String,
        val bankName: String,
        val sortCode: String,
        val abaRoutingNumber: String,
        val internationalBankAccountNumber: String,
        val bankIdentificationCode: String,
        val currency: Currency
)
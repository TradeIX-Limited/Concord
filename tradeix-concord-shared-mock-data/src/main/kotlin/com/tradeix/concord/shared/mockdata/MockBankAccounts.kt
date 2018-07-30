package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.messages.AddressRequestMessage
import com.tradeix.concord.shared.messages.BankAccountRequestMessage
import com.tradeix.concord.shared.models.Address
import com.tradeix.concord.shared.models.BankAccount

object MockBankAccounts {

    val BANK_ACCOUNT = BankAccount(
            accountName = "Mr. A. N. Other",
            accountNumber = "12345678",
            sortCode = "10-20-30",
            bankIdentifierCode = "DABAAZ01",
            internationalBankAccountNumber = "AZ 01 DABA012345 12345678",
            abaNumber = "01010101",
            bankName = "Bank of Breakfast Tea",
            bankAddress = Address(
                    residenceNameOrNumber = "Bank of England",
                    unitNameOrNumber = null,
                    street = "Threadneedle Street",
                    locality = null,
                    municipality = "London",
                    province = "City of London",
                    country = "United Kingdom",
                    postalCode = "EC2R 8AH"
            )
    )

    val BANK_ACCOUNT_REQUEST_MESSAGE = BankAccountRequestMessage(
            accountName = "Mr. A. N. Other",
            accountNumber = "12345678",
            sortCode = "10-20-30",
            bankIdentifierCode = "DABAAZ01",
            internationalBankAccountNumber = "AZ 01 DABA012345 12345678",
            abaNumber = "01010101",
            bankName = "Bank of Breakfast Tea",
            bankAddress = AddressRequestMessage(
                    residenceNameOrNumber = "Bank of England",
                    unitNameOrNumber = null,
                    street = "Threadneedle Street",
                    locality = null,
                    municipality = "London",
                    province = "City of London",
                    country = "United Kingdom",
                    postalCode = "EC2R 8AH"
            )
    )
}
package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.domain.models.Address

object MockAddresses {

    val BANK_OF_ENGLAND = Address(
            residenceNameOrNumber = "Bank of England",
            unitNameOrNumber = null,
            street = "Threadneedle Street",
            locality = null,
            municipality = "London",
            province = "City of London",
            country = "United Kingdom",
            postalCode = "EC2R 8AH"
    )

    val ROYAL_BANK_OF_SCOTLAND = Address(
            residenceNameOrNumber = "36",
            unitNameOrNumber = null,
            street = "St. Andrew Square",
            locality = null,
            municipality = "Edinburgh",
            province = "City of Edinburgh",
            country = "United Kingdom",
            postalCode = "EH2 2YB"
    )
}
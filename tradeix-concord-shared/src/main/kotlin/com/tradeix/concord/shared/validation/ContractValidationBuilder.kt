package com.tradeix.concord.shared.validation

import net.corda.core.transactions.LedgerTransaction
import java.security.PublicKey

class ContractValidationBuilder(
        context: ValidationContext,
        obj: LedgerTransaction?,
        val signers: List<PublicKey> = emptyList()
) : ValidationBuilder<LedgerTransaction>(context, obj)
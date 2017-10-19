package com.tradeix.concord.messages

import java.math.BigDecimal
import java.util.*

data class TradeAssetAmendmentRequestMessage(
        val linearId: UUID?,
        val assetId: String?,
        val amount: BigDecimal?,
        val currency: String?)
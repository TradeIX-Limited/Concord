package com.tradeix.concord.enumerations

import com.tradeix.concord.models.TradeAsset
import org.junit.Test
import kotlin.test.assertEquals

class TradeAssetStateTests {
    companion object {
        private val PURCHASE_ORDER = "PURCHASE_ORDER"
        private val INVOICE = "INVOICE"
    }

    @Test
    fun `Value of PURCHASE_ORDER should result in correct TradeAssetStatus value`() {
        val status = TradeAsset.TradeAssetStatus.valueOf(PURCHASE_ORDER)
        assertEquals(TradeAsset.TradeAssetStatus.PURCHASE_ORDER, status)
    }

    @Test
    fun `Value of INVOICE should result in correct TradeAssetStatus value`() {
        val status = TradeAsset.TradeAssetStatus.valueOf(INVOICE)
        assertEquals(TradeAsset.TradeAssetStatus.INVOICE, status)
    }
}
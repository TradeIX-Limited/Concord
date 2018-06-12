package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.messages.CancellationRequestMessage
import com.tradeix.concord.shared.messages.OwnershipRequestMessage
import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderMessage
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_1
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_2
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_3
import java.math.BigDecimal

object MockMessages {



    val PURCHASE_ORDER_ISSUANCE_REQUEST_MESSAGE = PurchaseOrderMessage(
            externalId = "PURCHASE_ORDER_001",
            buyer = BUYER_1_NAME.toString(),
            supplier = SUPPLIER_1_NAME.toString(),
            reference = "REF_PURCHASE_ORDER_001",
            value = BigDecimal.valueOf(123.45),
            currency = "GBP",
            earliestShipment = LOCAL_DATE_TIME_PAST_2,
            latestShipment = LOCAL_DATE_TIME_PAST_3,
            portOfShipment = "Portsmouth",
            descriptionOfGoods = "100 units of stock",
            deliveryTerms = "Subject to contract"
    )

    val PURCHASE_ORDER_AMENDMENT_REQUEST_MESSAGE = PurchaseOrderMessage(
            externalId = "PURCHASE_ORDER_001",
            buyer = BUYER_1_NAME.toString(),
            supplier = SUPPLIER_1_NAME.toString(),
            reference = "REF_PURCHASE_ORDER_001_AMENDED",
            value = BigDecimal.valueOf(678.90),
            currency = "GBP",
            earliestShipment = LOCAL_DATE_TIME_PAST_2,
            latestShipment = LOCAL_DATE_TIME_PAST_3,
            portOfShipment = "Portsmouth",
            descriptionOfGoods = "300 units of stock (amended)",
            deliveryTerms = "Subject to contract"
    )

    val PURCHASE_ORDER_CHANGE_OWNER_REQUEST_MESSAGE = OwnershipRequestMessage(
            externalId = "PURCHASE_ORDER_001",
            owner = FUNDER_1_NAME.toString()
    )

    val PURCHASE_ORDER_CANCELLATION_REQUEST_MESSAGE = CancellationRequestMessage(
            externalId = "PURCHASE_ORDER_001"
    )
}
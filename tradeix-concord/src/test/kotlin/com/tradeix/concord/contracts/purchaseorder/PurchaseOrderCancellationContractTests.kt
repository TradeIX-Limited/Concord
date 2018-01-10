package com.tradeix.concord.contracts.purchaseorder

import com.tradeix.concord.TestValueHelper
import com.tradeix.concord.TestValueHelper.BUYER
import com.tradeix.concord.TestValueHelper.BUYER_PUBKEY
import com.tradeix.concord.TestValueHelper.CONDUCTOR
import com.tradeix.concord.TestValueHelper.CONDUCTOR_PUBKEY
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_01
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_02
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_03
import com.tradeix.concord.TestValueHelper.DELIVERY_TERMS
import com.tradeix.concord.TestValueHelper.DESCRIPTION_OF_GOODS
import com.tradeix.concord.TestValueHelper.LINEAR_ID
import com.tradeix.concord.TestValueHelper.ONE_POUND
import com.tradeix.concord.TestValueHelper.PORT_OF_SHIPMENT
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_REFERENCE
import com.tradeix.concord.TestValueHelper.SUPPLIER
import com.tradeix.concord.TestValueHelper.SUPPLIER_PUBKEY
import com.tradeix.concord.contracts.PurchaseOrderContract
import com.tradeix.concord.contracts.PurchaseOrderContract.Companion.PURCHASE_ORDER_CONTRACT_ID
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.testing.ledger
import net.corda.testing.setCordappPackages
import net.corda.testing.unsetCordappPackages
import org.junit.After
import org.junit.Before
import org.junit.Test

class TradeAssetStateCancellationContractTests {
    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
    }

    @Test
    fun `PurchaseOrderState Cancellation transaction must include the Cancel command`() {
        ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = LINEAR_ID,
                            owner = BUYER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR,
                            reference = PURCHASE_ORDER_REFERENCE,
                            amount = ONE_POUND,
                            created = DATE_INSTANT_01,
                            earliestShipment = DATE_INSTANT_02,
                            latestShipment = DATE_INSTANT_03,
                            portOfShipment = PORT_OF_SHIPMENT,
                            descriptionOfGoods = DESCRIPTION_OF_GOODS,
                            deliveryTerms = DELIVERY_TERMS
                    )
                }
                fails()
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    PurchaseOrderContract.Commands.Cancel()
                }
                verifies()
            }
        }
    }

    @Test
    fun `PurchaseOrderState Cancellation transaction must only consume one input`() {
        ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = LINEAR_ID,
                            owner = BUYER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR,
                            reference = PURCHASE_ORDER_REFERENCE,
                            amount = ONE_POUND,
                            created = DATE_INSTANT_01,
                            earliestShipment = DATE_INSTANT_02,
                            latestShipment = DATE_INSTANT_03,
                            portOfShipment = PORT_OF_SHIPMENT,
                            descriptionOfGoods = DESCRIPTION_OF_GOODS,
                            deliveryTerms = DELIVERY_TERMS
                    )
                }
                input(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = LINEAR_ID,
                            owner = BUYER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR,
                            reference = PURCHASE_ORDER_REFERENCE,
                            amount = ONE_POUND,
                            created = DATE_INSTANT_01,
                            earliestShipment = DATE_INSTANT_02,
                            latestShipment = DATE_INSTANT_03,
                            portOfShipment = PORT_OF_SHIPMENT,
                            descriptionOfGoods = DESCRIPTION_OF_GOODS,
                            deliveryTerms = DELIVERY_TERMS
                    )
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    PurchaseOrderContract.Commands.Cancel()
                }
                failsWith(PurchaseOrderContract.Commands.Cancel.CONTRACT_RULE_INPUTS)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Cancellation transaction must not create any outputs`() {
        ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = LINEAR_ID,
                            owner = BUYER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR,
                            reference = PURCHASE_ORDER_REFERENCE,
                            amount = ONE_POUND,
                            created = DATE_INSTANT_01,
                            earliestShipment = DATE_INSTANT_02,
                            latestShipment = DATE_INSTANT_03,
                            portOfShipment = PORT_OF_SHIPMENT,
                            descriptionOfGoods = DESCRIPTION_OF_GOODS,
                            deliveryTerms = DELIVERY_TERMS
                    )
                }
                output(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = LINEAR_ID,
                            owner = BUYER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR,
                            reference = PURCHASE_ORDER_REFERENCE,
                            amount = ONE_POUND,
                            created = DATE_INSTANT_01,
                            earliestShipment = DATE_INSTANT_02,
                            latestShipment = DATE_INSTANT_03,
                            portOfShipment = PORT_OF_SHIPMENT,
                            descriptionOfGoods = DESCRIPTION_OF_GOODS,
                            deliveryTerms = DELIVERY_TERMS
                    )
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    PurchaseOrderContract.Commands.Cancel()
                }
                failsWith(PurchaseOrderContract.Commands.Cancel.CONTRACT_RULE_OUTPUTS)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Cancellation transaction owner must be the buyer`() {
        ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = LINEAR_ID,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR,
                            reference = PURCHASE_ORDER_REFERENCE,
                            amount = ONE_POUND,
                            created = DATE_INSTANT_01,
                            earliestShipment = DATE_INSTANT_02,
                            latestShipment = DATE_INSTANT_03,
                            portOfShipment = PORT_OF_SHIPMENT,
                            descriptionOfGoods = DESCRIPTION_OF_GOODS,
                            deliveryTerms = DELIVERY_TERMS
                    )
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    PurchaseOrderContract.Commands.Cancel()
                }
                failsWith(PurchaseOrderContract.Commands.Cancel.CONTRACT_RULE_OWNER)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Cancellation transaction all participants must sign (buyer must sign)`() {
        ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = LINEAR_ID,
                            owner = BUYER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR,
                            reference = PURCHASE_ORDER_REFERENCE,
                            amount = ONE_POUND,
                            created = DATE_INSTANT_01,
                            earliestShipment = DATE_INSTANT_02,
                            latestShipment = DATE_INSTANT_03,
                            portOfShipment = PORT_OF_SHIPMENT,
                            descriptionOfGoods = DESCRIPTION_OF_GOODS,
                            deliveryTerms = DELIVERY_TERMS
                    )
                }
                command(SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    PurchaseOrderContract.Commands.Cancel()
                }
                failsWith(PurchaseOrderContract.Commands.Cancel.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Cancellation transaction all participants must sign (supplier must sign)`() {
        ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = LINEAR_ID,
                            owner = BUYER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR,
                            reference = PURCHASE_ORDER_REFERENCE,
                            amount = ONE_POUND,
                            created = DATE_INSTANT_01,
                            earliestShipment = DATE_INSTANT_02,
                            latestShipment = DATE_INSTANT_03,
                            portOfShipment = PORT_OF_SHIPMENT,
                            descriptionOfGoods = DESCRIPTION_OF_GOODS,
                            deliveryTerms = DELIVERY_TERMS
                    )
                }
                command(BUYER_PUBKEY, CONDUCTOR_PUBKEY) {
                    PurchaseOrderContract.Commands.Cancel()
                }
                failsWith(PurchaseOrderContract.Commands.Cancel.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Cancellation transaction all participants must sign (conductor must sign)`() {
        ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID) {
                    PurchaseOrderState(
                            linearId = LINEAR_ID,
                            owner = BUYER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR,
                            reference = PURCHASE_ORDER_REFERENCE,
                            amount = ONE_POUND,
                            created = DATE_INSTANT_01,
                            earliestShipment = DATE_INSTANT_02,
                            latestShipment = DATE_INSTANT_03,
                            portOfShipment = PORT_OF_SHIPMENT,
                            descriptionOfGoods = DESCRIPTION_OF_GOODS,
                            deliveryTerms = DELIVERY_TERMS
                    )
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY) {
                    PurchaseOrderContract.Commands.Cancel()
                }
                failsWith(PurchaseOrderContract.Commands.Cancel.CONTRACT_RULE_SIGNERS)
            }
        }
    }
}
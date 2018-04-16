package com.tradeix.concord.contracts.purchaseorder

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
import com.tradeix.concord.TestValueHelper.TEN_POUNDS
import com.tradeix.concord.contracts.PurchaseOrderContract
import com.tradeix.concord.contracts.PurchaseOrderContract.Companion.PURCHASE_ORDER_CONTRACT_ID
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.testing.node.MockServices
import net.corda.testing.node.ledger
import org.junit.Test

class PurchaseOrderStateAmendmentContractTests {
    private var ledgerServices = MockServices(listOf("com.tradeix.concord.contracts"))

    @Test
    fun `PurchaseOrderState Amendment transaction must include the Amend command`() {
        ledgerServices.ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = ONE_POUND,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                output(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = TEN_POUNDS,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                fails()
                command(
                        listOf(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY),
                        PurchaseOrderContract.Commands.Amend()
                )
                verifies()
            }
        }
    }

    @Test
    fun `PurchaseOrderState Amendment transaction must consume only one input`() {
        ledgerServices.ledger {
            transaction {
                output(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = TEN_POUNDS,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                command(
                        listOf(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY),
                        PurchaseOrderContract.Commands.Amend())
                failsWith(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_INPUTS)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Amendment transaction must create only one output`() {
        ledgerServices.ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = ONE_POUND,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                command(
                        listOf(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY),
                        PurchaseOrderContract.Commands.Amend()
                )
                failsWith(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_OUTPUTS)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Amendment transaction buyer and supplier cannot be the same entity`() {
        ledgerServices.ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = ONE_POUND,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                output(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = BUYER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = TEN_POUNDS,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                fails()
                command(
                        listOf(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY),
                        PurchaseOrderContract.Commands.Amend()
                )
                failsWith(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_ENTITIES)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Amendment transaction buyer must be the owner`() {
        ledgerServices.ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = ONE_POUND,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                output(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = SUPPLIER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = TEN_POUNDS,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                fails()
                command(
                        listOf(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY),
                        PurchaseOrderContract.Commands.Amend())
                failsWith(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_OWNER)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Amendment transaction all participants must sign (buyer must sign)`() {
        ledgerServices.ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = SUPPLIER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = ONE_POUND,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                output(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = TEN_POUNDS,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                fails()
                command(
                        listOf(SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY),
                        PurchaseOrderContract.Commands.Amend())
                failsWith(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Amendment transaction all participants must sign (supplier must sign)`() {
        ledgerServices.ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = ONE_POUND,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                output(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = TEN_POUNDS,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                fails()
                command(
                        listOf(BUYER_PUBKEY, CONDUCTOR_PUBKEY),
                        PurchaseOrderContract.Commands.Amend()
                )
                failsWith(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `PurchaseOrderState Amendment transaction all participants must sign (conductor must sign)`() {
        ledgerServices.ledger {
            transaction {
                input(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = ONE_POUND,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                output(PURCHASE_ORDER_CONTRACT_ID, PurchaseOrderState(
                        linearId = LINEAR_ID,
                        owner = BUYER.party,
                        buyer = BUYER.party,
                        supplier = SUPPLIER.party,
                        conductor = CONDUCTOR.party,
                        reference = PURCHASE_ORDER_REFERENCE,
                        amount = TEN_POUNDS,
                        created = DATE_INSTANT_01,
                        earliestShipment = DATE_INSTANT_02,
                        latestShipment = DATE_INSTANT_03,
                        portOfShipment = PORT_OF_SHIPMENT,
                        descriptionOfGoods = DESCRIPTION_OF_GOODS,
                        deliveryTerms = DELIVERY_TERMS
                )
                )
                fails()
                command(
                        listOf(BUYER_PUBKEY, SUPPLIER_PUBKEY),
                        PurchaseOrderContract.Commands.Amend()
                )
                failsWith(PurchaseOrderContract.Commands.Amend.CONTRACT_RULE_SIGNERS)
            }
        }
    }
}
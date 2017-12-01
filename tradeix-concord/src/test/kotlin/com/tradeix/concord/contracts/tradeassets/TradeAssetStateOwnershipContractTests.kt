package com.tradeix.concord.contracts.tradeassets

import com.tradeix.concord.TestValueHelper.BUYER
import com.tradeix.concord.TestValueHelper.BUYER_PUBKEY
import com.tradeix.concord.TestValueHelper.CONDUCTOR
import com.tradeix.concord.TestValueHelper.CONDUCTOR_PUBKEY
import com.tradeix.concord.TestValueHelper.FUNDER
import com.tradeix.concord.TestValueHelper.FUNDER_PUBKEY
import com.tradeix.concord.TestValueHelper.LINEAR_ID
import com.tradeix.concord.TestValueHelper.SUPPLIER
import com.tradeix.concord.TestValueHelper.SUPPLIER_PUBKEY
import com.tradeix.concord.TestValueHelper.TRADE_ASSET_INVOICE_ONE_POUND
import com.tradeix.concord.contracts.TradeAssetContract
import com.tradeix.concord.contracts.TradeAssetContract.Companion.TRADE_ASSET_CONTRACT_ID
import com.tradeix.concord.states.TradeAssetState
import net.corda.testing.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class TradeAssetStateOwnershipContractTests {
    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
    }

    @Test
    fun `On ownership change, the transaction must include the ChangeOwner command`() {
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = FUNDER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                fails()
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                verifies()
            }
        }
    }

    @Test
    fun `On ownership change, only one input should be consumed`() {
        ledger {
            transaction {
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = FUNDER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                failsWith(TradeAssetContract.Commands.ChangeOwner.CONTRACT_RULE_INPUTS)
            }
        }
    }

    @Test
    fun `On ownership change, only one output state should be created`() {
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = FUNDER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = FUNDER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                failsWith(TradeAssetContract.Commands.ChangeOwner.CONTRACT_RULE_OUTPUTS)
            }
        }
    }

    @Test
    fun `On ownership change, the supplier and the new owner cannot be the same entity`() {
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                failsWith(TradeAssetContract.Commands.ChangeOwner.CONTRACT_RULE_ENTITIES)
            }
        }
    }

    @Test
    fun `On ownership change, all participants must sign the transaction (owner must sign)`() {
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = FUNDER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                failsWith(TradeAssetContract.Commands.ChangeOwner.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `On ownership change, all participants must sign the transaction (buyer must sign)`() {
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = FUNDER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(SUPPLIER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                failsWith(TradeAssetContract.Commands.ChangeOwner.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `On ownership change, all participants must sign the transaction (supplier must sign)`() {
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = FUNDER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                failsWith(TradeAssetContract.Commands.ChangeOwner.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `On ownership change, all participants must sign the transaction (conductor must sign)`() {
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = FUNDER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, FUNDER_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                failsWith(TradeAssetContract.Commands.ChangeOwner.CONTRACT_RULE_SIGNERS)
            }
        }
    }
}

package com.tradeix.concord.contracts.tradeassets

import com.tradeix.concord.TestValueHelper.BUYER
import com.tradeix.concord.TestValueHelper.BUYER_PUBKEY
import com.tradeix.concord.TestValueHelper.CONDUCTOR
import com.tradeix.concord.TestValueHelper.CONDUCTOR_PUBKEY
import com.tradeix.concord.TestValueHelper.FUNDER
import com.tradeix.concord.TestValueHelper.LINEAR_ID
import com.tradeix.concord.TestValueHelper.SUPPLIER
import com.tradeix.concord.TestValueHelper.SUPPLIER_PUBKEY
import com.tradeix.concord.TestValueHelper.TRADE_ASSET_INVOICE_ONE_POUND
import com.tradeix.concord.TestValueHelper.TRADE_ASSET_INVOICE_TEN_POUNDS
import com.tradeix.concord.contracts.TradeAssetContract
import com.tradeix.concord.contracts.TradeAssetContract.Companion.TRADE_ASSET_CONTRACT_ID
import com.tradeix.concord.states.TradeAssetState
import net.corda.testing.*
import org.junit.After
import org.junit.Before
import org.junit.Test

class TradeAssetStateAmendmentContractTests {
    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
    }

    @Test
    fun `On amendment, the transaction must include the Amend command`() {
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
                            tradeAsset = TRADE_ASSET_INVOICE_TEN_POUNDS,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                fails()
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                verifies()
            }
        }
    }

    @Test
    fun `On amendment, only one input should be consumed`() {
        ledger {
            transaction {
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_TEN_POUNDS,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                failsWith(TradeAssetContract.Commands.Amend.CONTRACT_RULE_INPUTS)
            }
        }
    }

    @Test
    fun `On amendment, only one output state should be created`() {
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
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_TEN_POUNDS,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                failsWith(TradeAssetContract.Commands.Amend.CONTRACT_RULE_OUTPUTS)
            }
        }
    }

    @Test
    fun `On amendment, the supplier must be the owner`() {
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
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
                            tradeAsset = TRADE_ASSET_INVOICE_TEN_POUNDS,
                            owner = FUNDER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                failsWith(TradeAssetContract.Commands.Amend.CONTRACT_RULE_AMEND)
            }
        }
    }

    @Test
    fun `On amendment, all participants must sign the transaction (owner must sign)`() {
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
                            tradeAsset = TRADE_ASSET_INVOICE_TEN_POUNDS,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                failsWith(TradeAssetContract.Commands.Amend.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (buyer must sign)`() {
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
                            tradeAsset = TRADE_ASSET_INVOICE_TEN_POUNDS,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                failsWith(TradeAssetContract.Commands.Amend.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (supplier must sign)`() {
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
                            tradeAsset = TRADE_ASSET_INVOICE_TEN_POUNDS,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                failsWith(TradeAssetContract.Commands.Amend.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (conductor must sign)`() {
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
                            tradeAsset = TRADE_ASSET_INVOICE_TEN_POUNDS,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                failsWith(TradeAssetContract.Commands.Amend.CONTRACT_RULE_SIGNERS)
            }
        }
    }
}
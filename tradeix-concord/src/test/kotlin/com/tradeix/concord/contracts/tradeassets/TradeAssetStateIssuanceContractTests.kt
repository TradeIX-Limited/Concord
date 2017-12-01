package com.tradeix.concord.contracts.tradeassets

import com.tradeix.concord.TestValueHelper.BUYER
import com.tradeix.concord.TestValueHelper.BUYER_PUBKEY
import com.tradeix.concord.TestValueHelper.CONDUCTOR
import com.tradeix.concord.TestValueHelper.CONDUCTOR_PUBKEY
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

class TradeAssetStateIssuanceContractTests {
    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
    }

    @Test
    fun `On issuance, the transaction must include the Issue command`() {
        ledger {
            transaction {
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                fails()
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                verifies()
            }
        }
    }

    @Test
    fun `On issuance, zero input states should be consumed`() {
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
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                failsWith(TradeAssetContract.Commands.Issue.CONTRACT_RULE_INPUTS)
            }
        }
    }

    @Test
    fun `On issuance, only one output state should be created`() {
        ledger {
            transaction {
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
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                failsWith(TradeAssetContract.Commands.Issue.CONTRACT_RULE_OUTPUTS)
            }
        }
    }

    @Test
    fun `On issuance, the buyer and the supplier cannot be the same entity`() {
        ledger {
            transaction {
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = BUYER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                failsWith(TradeAssetContract.Commands.Issue.CONTRACT_RULE_ENTITIES)
            }
        }
    }

    @Test
    fun `On issuance, all participants must sign the transaction (buyer must sign)`() {
        ledger {
            transaction {
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(SUPPLIER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                failsWith(TradeAssetContract.Commands.Issue.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `On issuance, all participants must sign the transaction (supplier must sign)`() {
        ledger {
            transaction {
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, CONDUCTOR_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                failsWith(TradeAssetContract.Commands.Issue.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `On issuance, all participants must sign the transaction (conductor must sign)`() {
        ledger {
            transaction {
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = LINEAR_ID,
                            tradeAsset = TRADE_ASSET_INVOICE_ONE_POUND,
                            owner = SUPPLIER,
                            buyer = BUYER,
                            supplier = SUPPLIER,
                            conductor = CONDUCTOR)
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                failsWith(TradeAssetContract.Commands.Issue.CONTRACT_RULE_SIGNERS)
            }
        }
    }
}

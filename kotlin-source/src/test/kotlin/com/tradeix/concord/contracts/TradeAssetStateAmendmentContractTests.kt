package com.tradeix.concord.contracts

import com.tradeix.concord.contracts.TradeAssetContract.Companion.TRADE_ASSET_CONTRACT_ID
import com.tradeix.concord.models.TradeAsset
import com.tradeix.concord.states.TradeAssetState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.finance.DOLLARS
import net.corda.finance.POUNDS
import net.corda.testing.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

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
    fun `Transaction must include Amend command`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val originalTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
        val amendedTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 2.DOLLARS)
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = originalTradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = amendedTradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                fails()
                command(ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                verifies()
            }
        }
    }

    @Test
    fun `Only one input should be consumed when amending a trade asset`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
        ledger {
            transaction {
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = tradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                `fails with`("Only one input should be consumed when amending a trade asset.")
            }
        }
    }

    @Test
    fun `Only one output state should be created when amending a trade asset`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = tradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                `fails with`("Only one output state should be created when amending a trade asset.")
            }
        }
    }

    @Test
    fun `The supplier must be the owner when amending a trade asset`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val originalTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
        val amendedTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 2.DOLLARS)
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = originalTradeAsset,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = amendedTradeAsset,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                `fails with`("The supplier must be the owner when amending a trade asset.")
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (owner must sign)`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val originalTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
        val amendedTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 2.DOLLARS)
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = originalTradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = amendedTradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(ALICE_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                `fails with`("All participants must sign the transaction.")
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (buyer must sign)`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val originalTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
        val amendedTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 2.DOLLARS)
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = originalTradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = amendedTradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(BOB_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                `fails with`("All participants must sign the transaction.")
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (supplier must sign)`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val originalTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
        val amendedTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 2.DOLLARS)
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = originalTradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = amendedTradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(ALICE_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                `fails with`("All participants must sign the transaction.")
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (conductor must sign)`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val originalTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
        val amendedTradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.TradeAssetStatus.INVOICE, 2.DOLLARS)
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = originalTradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = amendedTradeAsset,
                            owner = ALICE,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.Amend()
                }
                `fails with`("All participants must sign the transaction.")
            }
        }
    }
}
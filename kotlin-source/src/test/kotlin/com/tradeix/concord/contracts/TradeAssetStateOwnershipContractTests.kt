package com.tradeix.concord.contracts

import com.tradeix.concord.contracts.TradeAssetContract.Companion.TRADE_ASSET_CONTRACT_ID
import com.tradeix.concord.models.TradeAsset
import com.tradeix.concord.states.TradeAssetState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.finance.POUNDS
import net.corda.testing.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*

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
    fun `Transaction must include ChangeOwner command`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset(TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
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
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = tradeAsset,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                fails()
                command(MEGA_CORP_PUBKEY, ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                verifies()
            }
        }
    }

    @Test
    fun `Only one input should be consumed when changing ownership of a trade asset`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset(TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
        ledger {
            transaction {
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = tradeAsset,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(MEGA_CORP_PUBKEY, ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                `fails with`("At least one input should be consumed when changing ownership of a trade asset.")
            }
        }
    }

    @Test
    fun `Only one output state should be created when changing ownership of a trade asset`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset(TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
        ledger {
            transaction {
                input(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = tradeAsset,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(MEGA_CORP_PUBKEY, ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                `fails with`("The number of output and input states should be equal when changing ownership of a trade asset.")
            }
        }
    }

    @Test
    fun `The supplier and the new owner cannot be the same entity`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset(TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
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
                    TradeAssetContract.Commands.ChangeOwner()
                }
                `fails with`("The supplier and the new owner cannot be the same entity.")
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (owner must sign)`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset(TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
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
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = tradeAsset,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(MEGA_CORP_PUBKEY) { TradeAssetContract.Commands.ChangeOwner() }
                `fails with`("All participants must sign the transaction.")
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (buyer must sign)`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset(TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
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
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = tradeAsset,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(BOB_PUBKEY) { TradeAssetContract.Commands.ChangeOwner() }
                `fails with`("All participants must sign the transaction.")
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (supplier must sign)`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset(TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
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
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = tradeAsset,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(ALICE_PUBKEY) { TradeAssetContract.Commands.ChangeOwner() }
                `fails with`("All participants must sign the transaction.")
            }
        }
    }

    @Test
    fun `All participants must sign the transaction (conductor must sign)`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset(TradeAsset.TradeAssetStatus.INVOICE, 1.POUNDS)
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
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = tradeAsset,
                            owner = MEGA_CORP,
                            buyer = BOB,
                            supplier = ALICE,
                            conductor = BIG_CORP)
                }
                command(BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.ChangeOwner()
                }
                `fails with`("All participants must sign the transaction.")
            }
        }
    }
}

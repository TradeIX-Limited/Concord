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
    fun `Trade Asset Issuance transaction must include Issue command`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.STATE_ISSUED, 1.POUNDS)
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
                fails()
                command(ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                verifies()
            }
        }
    }

    @Test
    fun `Trade Asset Issuance transaction must have no inputs`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.STATE_ISSUED, 1.POUNDS)
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
                    TradeAssetContract.Commands.Issue()
                }
                `fails with`("No inputs should be consumed when issuing a purchase order.")
            }
        }
    }

    @Test
    fun `Trade Asset Issuance transaction must have one output`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.STATE_ISSUED, 1.POUNDS)
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
                    TradeAssetContract.Commands.Issue()
                }
                `fails with`("Only one output state should be created.")
            }
        }
    }

    @Test
    fun `Trade Asset Issuance transaction must be signed by the buyer`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.STATE_ISSUED, 1.POUNDS)
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
                command(BOB_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                `fails with`("All of the participants must be signers.")
            }
        }
    }

    @Test
    fun `Trade Asset Issuance transaction must be signed by the supplier`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.STATE_ISSUED, 1.POUNDS)
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
                command(ALICE_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                `fails with`("All of the participants must be signers.")
            }
        }
    }

    @Test
    fun `Trade Asset Issuance transaction must be signed by conductor`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.STATE_ISSUED, 1.POUNDS)
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
                command(BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                `fails with`("All of the participants must be signers.")
            }
        }
    }

    @Test
    fun `Trade Asset Issuance transaction cannot have buyer and supplier as the same entity`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val tradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.STATE_ISSUED, 1.POUNDS)
        ledger {
            transaction {
                output(TRADE_ASSET_CONTRACT_ID) {
                    TradeAssetState(
                            linearId = linearId,
                            tradeAsset = tradeAsset,
                            owner = BOB,
                            buyer = BOB,
                            supplier = BOB,
                            conductor = BIG_CORP)
                }
                command(ALICE_PUBKEY, BOB_PUBKEY, BIG_CORP_PUBKEY) {
                    TradeAssetContract.Commands.Issue()
                }
                `fails with`("The buyer and the supplier cannot be the same entity.")
            }
        }
    }
}

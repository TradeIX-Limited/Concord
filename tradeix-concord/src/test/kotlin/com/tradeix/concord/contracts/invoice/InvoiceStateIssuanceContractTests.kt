package com.tradeix.concord.contracts.invoice

import com.tradeix.concord.TestValueHelper.BUYER
import com.tradeix.concord.TestValueHelper.BUYER_PUBKEY
import com.tradeix.concord.TestValueHelper.CANCELLED
import com.tradeix.concord.TestValueHelper.COMPOSER_PROGRAM_ID
import com.tradeix.concord.TestValueHelper.CONDUCTOR
import com.tradeix.concord.TestValueHelper.CONDUCTOR_PUBKEY
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_01
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_02
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_03
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_04
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_05
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_06
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_07
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_08
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_09
import com.tradeix.concord.TestValueHelper.FUNDER
import com.tradeix.concord.TestValueHelper.FUNDER_PUBKEY
import com.tradeix.concord.TestValueHelper.GBP
import com.tradeix.concord.TestValueHelper.HASH
import com.tradeix.concord.TestValueHelper.INVOICE_NUMBER
import com.tradeix.concord.TestValueHelper.INVOICE_TYPE
import com.tradeix.concord.TestValueHelper.INVOICE_VERSION
import com.tradeix.concord.TestValueHelper.LINEAR_ID
import com.tradeix.concord.TestValueHelper.OFFER_ID
import com.tradeix.concord.TestValueHelper.ONE_POUND
import com.tradeix.concord.TestValueHelper.ORIGINATION_NETWORK
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_ID
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_NUMBER
import com.tradeix.concord.TestValueHelper.REFERENCE
import com.tradeix.concord.TestValueHelper.REJECTION_REASON
import com.tradeix.concord.TestValueHelper.SITE_ID
import com.tradeix.concord.TestValueHelper.STATUS
import com.tradeix.concord.TestValueHelper.SUPPLIER
import com.tradeix.concord.TestValueHelper.SUPPLIER_PUBKEY
import com.tradeix.concord.TestValueHelper.TEN_POUNDS
import com.tradeix.concord.TestValueHelper.TIX_INVOICE_VERSION
import com.tradeix.concord.contracts.InvoiceContract
import com.tradeix.concord.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.states.InvoiceState
import net.corda.testing.ledger
import net.corda.testing.setCordappPackages
import net.corda.testing.unsetCordappPackages
import org.junit.After
import org.junit.Before
import org.junit.Test

class InvoiceStateIssuanceContractTests {
    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
    }

    @Test
    fun `InvoiceState Issuance transaction must include the Issue command`() {
        ledger {
            transaction {
                output(INVOICE_CONTRACT_ID) {
                    InvoiceState(
                            LINEAR_ID,
                            SUPPLIER,
                            BUYER,
                            SUPPLIER,
                            CONDUCTOR,
                            INVOICE_VERSION,
                            DATE_INSTANT_01,
                            TIX_INVOICE_VERSION,
                            INVOICE_NUMBER,
                            INVOICE_TYPE,
                            REFERENCE,
                            DATE_INSTANT_02,
                            OFFER_ID,
                            TEN_POUNDS,
                            ONE_POUND,
                            DATE_INSTANT_03,
                            DATE_INSTANT_04,
                            DATE_INSTANT_05,
                            DATE_INSTANT_06,
                            DATE_INSTANT_07,
                            DATE_INSTANT_08,
                            STATUS,
                            REJECTION_REASON,
                            TEN_POUNDS,
                            TEN_POUNDS,
                            DATE_INSTANT_09,
                            DATE_INSTANT_01,
                            ONE_POUND,
                            ONE_POUND,
                            CANCELLED,
                            DATE_INSTANT_02,
                            ORIGINATION_NETWORK,
                            HASH,
                            GBP,
                            SITE_ID,
                            PURCHASE_ORDER_NUMBER,
                            PURCHASE_ORDER_ID,
                            COMPOSER_PROGRAM_ID
                    )
                }
                fails()
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    InvoiceContract.Commands.Issue()
                }
                verifies()
            }
        }
    }

    @Test
    fun `InvoiceState Issuance transaction must consume zero inputs`() {
        ledger {
            transaction {
                input(INVOICE_CONTRACT_ID) {
                    InvoiceState(
                            LINEAR_ID,
                            SUPPLIER,
                            BUYER,
                            SUPPLIER,
                            CONDUCTOR,
                            INVOICE_VERSION,
                            DATE_INSTANT_01,
                            TIX_INVOICE_VERSION,
                            INVOICE_NUMBER,
                            INVOICE_TYPE,
                            REFERENCE,
                            DATE_INSTANT_02,
                            OFFER_ID,
                            TEN_POUNDS,
                            ONE_POUND,
                            DATE_INSTANT_03,
                            DATE_INSTANT_04,
                            DATE_INSTANT_05,
                            DATE_INSTANT_06,
                            DATE_INSTANT_07,
                            DATE_INSTANT_08,
                            STATUS,
                            REJECTION_REASON,
                            TEN_POUNDS,
                            TEN_POUNDS,
                            DATE_INSTANT_09,
                            DATE_INSTANT_01,
                            ONE_POUND,
                            ONE_POUND,
                            CANCELLED,
                            DATE_INSTANT_02,
                            ORIGINATION_NETWORK,
                            HASH,
                            GBP,
                            SITE_ID,
                            PURCHASE_ORDER_NUMBER,
                            PURCHASE_ORDER_ID,
                            COMPOSER_PROGRAM_ID
                    )
                }
                output(INVOICE_CONTRACT_ID) {
                    InvoiceState(
                            LINEAR_ID,
                            SUPPLIER,
                            BUYER,
                            SUPPLIER,
                            CONDUCTOR,
                            INVOICE_VERSION,
                            DATE_INSTANT_01,
                            TIX_INVOICE_VERSION,
                            INVOICE_NUMBER,
                            INVOICE_TYPE,
                            REFERENCE,
                            DATE_INSTANT_02,
                            OFFER_ID,
                            TEN_POUNDS,
                            ONE_POUND,
                            DATE_INSTANT_03,
                            DATE_INSTANT_04,
                            DATE_INSTANT_05,
                            DATE_INSTANT_06,
                            DATE_INSTANT_07,
                            DATE_INSTANT_08,
                            STATUS,
                            REJECTION_REASON,
                            TEN_POUNDS,
                            TEN_POUNDS,
                            DATE_INSTANT_09,
                            DATE_INSTANT_01,
                            ONE_POUND,
                            ONE_POUND,
                            CANCELLED,
                            DATE_INSTANT_02,
                            ORIGINATION_NETWORK,
                            HASH,
                            GBP,
                            SITE_ID,
                            PURCHASE_ORDER_NUMBER,
                            PURCHASE_ORDER_ID,
                            COMPOSER_PROGRAM_ID
                    )
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    InvoiceContract.Commands.Issue()
                }
                failsWith(InvoiceContract.Commands.Issue.CONTRACT_RULE_INPUTS)
            }
        }
    }

    @Test
    fun `InvoiceState Issuance transaction must create only one output`() {
        ledger {
            transaction {
                output(INVOICE_CONTRACT_ID) {
                    InvoiceState(
                            LINEAR_ID,
                            SUPPLIER,
                            BUYER,
                            SUPPLIER,
                            CONDUCTOR,
                            INVOICE_VERSION,
                            DATE_INSTANT_01,
                            TIX_INVOICE_VERSION,
                            INVOICE_NUMBER,
                            INVOICE_TYPE,
                            REFERENCE,
                            DATE_INSTANT_02,
                            OFFER_ID,
                            TEN_POUNDS,
                            ONE_POUND,
                            DATE_INSTANT_03,
                            DATE_INSTANT_04,
                            DATE_INSTANT_05,
                            DATE_INSTANT_06,
                            DATE_INSTANT_07,
                            DATE_INSTANT_08,
                            STATUS,
                            REJECTION_REASON,
                            TEN_POUNDS,
                            TEN_POUNDS,
                            DATE_INSTANT_09,
                            DATE_INSTANT_01,
                            ONE_POUND,
                            ONE_POUND,
                            CANCELLED,
                            DATE_INSTANT_02,
                            ORIGINATION_NETWORK,
                            HASH,
                            GBP,
                            SITE_ID,
                            PURCHASE_ORDER_NUMBER,
                            PURCHASE_ORDER_ID,
                            COMPOSER_PROGRAM_ID
                    )
                }
                output(INVOICE_CONTRACT_ID) {
                    InvoiceState(
                            LINEAR_ID,
                            SUPPLIER,
                            BUYER,
                            SUPPLIER,
                            CONDUCTOR,
                            INVOICE_VERSION,
                            DATE_INSTANT_01,
                            TIX_INVOICE_VERSION,
                            INVOICE_NUMBER,
                            INVOICE_TYPE,
                            REFERENCE,
                            DATE_INSTANT_02,
                            OFFER_ID,
                            TEN_POUNDS,
                            ONE_POUND,
                            DATE_INSTANT_03,
                            DATE_INSTANT_04,
                            DATE_INSTANT_05,
                            DATE_INSTANT_06,
                            DATE_INSTANT_07,
                            DATE_INSTANT_08,
                            STATUS,
                            REJECTION_REASON,
                            TEN_POUNDS,
                            TEN_POUNDS,
                            DATE_INSTANT_09,
                            DATE_INSTANT_01,
                            ONE_POUND,
                            ONE_POUND,
                            CANCELLED,
                            DATE_INSTANT_02,
                            ORIGINATION_NETWORK,
                            HASH,
                            GBP,
                            SITE_ID,
                            PURCHASE_ORDER_NUMBER,
                            PURCHASE_ORDER_ID,
                            COMPOSER_PROGRAM_ID
                    )
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    InvoiceContract.Commands.Issue()
                }
                failsWith(InvoiceContract.Commands.Issue.CONTRACT_RULE_OUTPUTS)
            }
        }
    }

    @Test
    fun `InvoiceState issuance transaction all participants must sign (buyer must sign)`() {
        ledger {
            transaction {
                output(INVOICE_CONTRACT_ID) {
                    InvoiceState(
                            LINEAR_ID,
                            SUPPLIER,
                            BUYER,
                            SUPPLIER,
                            CONDUCTOR,
                            INVOICE_VERSION,
                            DATE_INSTANT_01,
                            TIX_INVOICE_VERSION,
                            INVOICE_NUMBER,
                            INVOICE_TYPE,
                            REFERENCE,
                            DATE_INSTANT_02,
                            OFFER_ID,
                            TEN_POUNDS,
                            ONE_POUND,
                            DATE_INSTANT_03,
                            DATE_INSTANT_04,
                            DATE_INSTANT_05,
                            DATE_INSTANT_06,
                            DATE_INSTANT_07,
                            DATE_INSTANT_08,
                            STATUS,
                            REJECTION_REASON,
                            TEN_POUNDS,
                            TEN_POUNDS,
                            DATE_INSTANT_09,
                            DATE_INSTANT_01,
                            ONE_POUND,
                            ONE_POUND,
                            CANCELLED,
                            DATE_INSTANT_02,
                            ORIGINATION_NETWORK,
                            HASH,
                            GBP,
                            SITE_ID,
                            PURCHASE_ORDER_NUMBER,
                            PURCHASE_ORDER_ID,
                            COMPOSER_PROGRAM_ID
                    )
                }
                command(SUPPLIER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    InvoiceContract.Commands.Issue()
                }
                failsWith(InvoiceContract.Commands.Issue.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `InvoiceState issuance transaction all participants must sign (supplier must sign)`() {
        ledger {
            transaction {
                output(INVOICE_CONTRACT_ID) {
                    InvoiceState(
                            LINEAR_ID,
                            SUPPLIER,
                            BUYER,
                            SUPPLIER,
                            CONDUCTOR,
                            INVOICE_VERSION,
                            DATE_INSTANT_01,
                            TIX_INVOICE_VERSION,
                            INVOICE_NUMBER,
                            INVOICE_TYPE,
                            REFERENCE,
                            DATE_INSTANT_02,
                            OFFER_ID,
                            TEN_POUNDS,
                            ONE_POUND,
                            DATE_INSTANT_03,
                            DATE_INSTANT_04,
                            DATE_INSTANT_05,
                            DATE_INSTANT_06,
                            DATE_INSTANT_07,
                            DATE_INSTANT_08,
                            STATUS,
                            REJECTION_REASON,
                            TEN_POUNDS,
                            TEN_POUNDS,
                            DATE_INSTANT_09,
                            DATE_INSTANT_01,
                            ONE_POUND,
                            ONE_POUND,
                            CANCELLED,
                            DATE_INSTANT_02,
                            ORIGINATION_NETWORK,
                            HASH,
                            GBP,
                            SITE_ID,
                            PURCHASE_ORDER_NUMBER,
                            PURCHASE_ORDER_ID,
                            COMPOSER_PROGRAM_ID
                    )
                }
                command(BUYER_PUBKEY, FUNDER_PUBKEY, CONDUCTOR_PUBKEY) {
                    InvoiceContract.Commands.Issue()
                }
                failsWith(InvoiceContract.Commands.Issue.CONTRACT_RULE_SIGNERS)
            }
        }
    }

    @Test
    fun `InvoiceState issuance transaction all participants must sign (conductor must sign)`() {
        ledger {
            transaction {
                output(INVOICE_CONTRACT_ID) {
                    InvoiceState(
                            LINEAR_ID,
                            SUPPLIER,
                            BUYER,
                            SUPPLIER,
                            CONDUCTOR,
                            INVOICE_VERSION,
                            DATE_INSTANT_01,
                            TIX_INVOICE_VERSION,
                            INVOICE_NUMBER,
                            INVOICE_TYPE,
                            REFERENCE,
                            DATE_INSTANT_02,
                            OFFER_ID,
                            TEN_POUNDS,
                            ONE_POUND,
                            DATE_INSTANT_03,
                            DATE_INSTANT_04,
                            DATE_INSTANT_05,
                            DATE_INSTANT_06,
                            DATE_INSTANT_07,
                            DATE_INSTANT_08,
                            STATUS,
                            REJECTION_REASON,
                            TEN_POUNDS,
                            TEN_POUNDS,
                            DATE_INSTANT_09,
                            DATE_INSTANT_01,
                            ONE_POUND,
                            ONE_POUND,
                            CANCELLED,
                            DATE_INSTANT_02,
                            ORIGINATION_NETWORK,
                            HASH,
                            GBP,
                            SITE_ID,
                            PURCHASE_ORDER_NUMBER,
                            PURCHASE_ORDER_ID,
                            COMPOSER_PROGRAM_ID
                    )
                }
                command(BUYER_PUBKEY, SUPPLIER_PUBKEY, FUNDER_PUBKEY) {
                    InvoiceContract.Commands.Issue()
                }
                failsWith(InvoiceContract.Commands.Issue.CONTRACT_RULE_SIGNERS)
            }
        }
    }
}
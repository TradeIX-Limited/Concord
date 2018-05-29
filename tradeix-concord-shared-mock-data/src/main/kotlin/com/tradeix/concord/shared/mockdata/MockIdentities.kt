package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_3_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_3_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.GUARANTOR_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.GUARANTOR_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.GUARANTOR_3_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.NOTARY_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.OBLIGEE_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.OBLIGEE_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.OBLIGEE_3_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.OBLIGOR_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.OBLIGOR_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.OBLIGOR_3_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_3_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.TRADEIX_NAME
import net.corda.testing.core.TestIdentity

object MockIdentities {

    val NOTARY_IDENTITY = TestIdentity(NOTARY_NAME)
    val TRADEIX_IDENTITY = TestIdentity(TRADEIX_NAME)

    val BUYER_1_IDENTITY = TestIdentity(BUYER_1_NAME)
    val BUYER_2_IDENTITY = TestIdentity(BUYER_2_NAME)
    val BUYER_3_IDENTITY = TestIdentity(BUYER_3_NAME)

    val SUPPLIER_1_IDENTITY = TestIdentity(SUPPLIER_1_NAME)
    val SUPPLIER_2_IDENTITY = TestIdentity(SUPPLIER_2_NAME)
    val SUPPLIER_3_IDENTITY = TestIdentity(SUPPLIER_3_NAME)

    val FUNDER_1_IDENTITY = TestIdentity(FUNDER_1_NAME)
    val FUNDER_2_IDENTITY = TestIdentity(FUNDER_2_NAME)
    val FUNDER_3_IDENTITY = TestIdentity(FUNDER_3_NAME)

    val OBLIGOR_1_IDENTITY = TestIdentity(OBLIGOR_1_NAME)
    val OBLIGOR_2_IDENTITY = TestIdentity(OBLIGOR_2_NAME)
    val OBLIGOR_3_IDENTITY = TestIdentity(OBLIGOR_3_NAME)

    val OBLIGEE_1_IDENTITY = TestIdentity(OBLIGEE_1_NAME)
    val OBLIGEE_2_IDENTITY = TestIdentity(OBLIGEE_2_NAME)
    val OBLIGEE_3_IDENTITY = TestIdentity(OBLIGEE_3_NAME)

    val GUARANTOR_1_IDENTITY = TestIdentity(GUARANTOR_1_NAME)
    val GUARANTOR_2_IDENTITY = TestIdentity(GUARANTOR_2_NAME)
    val GUARANTOR_3_IDENTITY = TestIdentity(GUARANTOR_3_NAME)
}
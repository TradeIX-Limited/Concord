package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.mockdata.MockIdentities.BUYER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.BUYER_2_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.BUYER_3_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_2_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_3_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_2_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_3_IDENTITY
import com.tradeix.concord.shared.models.Participant

object MockParticipants {

    val BUYER_1_PARTICIPANT = Participant(BUYER_1_IDENTITY.party, "Buyer 1")
    val BUYER_2_PARTICIPANT = Participant(BUYER_2_IDENTITY.party, "Buyer 2")
    val BUYER_3_PARTICIPANT = Participant(BUYER_3_IDENTITY.party, "Buyer 3")

    val SUPPLIER_1_PARTICIPANT = Participant(SUPPLIER_1_IDENTITY.party, "Supplier 1")
    val SUPPLIER_2_PARTICIPANT = Participant(SUPPLIER_2_IDENTITY.party, "Supplier 2")
    val SUPPLIER_3_PARTICIPANT = Participant(SUPPLIER_3_IDENTITY.party, "Supplier 3")

    val FUNDER_1_PARTICIPANT = Participant(FUNDER_1_IDENTITY.party, "Funder 1")
    val FUNDER_2_PARTICIPANT = Participant(FUNDER_2_IDENTITY.party, "Funder 2")
    val FUNDER_3_PARTICIPANT = Participant(FUNDER_3_IDENTITY.party, "Funder 3")
}
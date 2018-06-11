package com.tradeix.concord.shared.extensions

import net.corda.core.identity.AbstractParty
import java.security.PublicKey

fun Iterable<AbstractParty>.toOwningKeys(): List<PublicKey> {
    return this.map { it.owningKey }
}
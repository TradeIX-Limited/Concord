package com.tradeix.concord.exceptions

import net.corda.core.flows.FlowException

class FlowVerificationException(val verificationErrors: ArrayList<String>)
    : FlowException("Flow verification failed: ${verificationErrors.joinToString()}")
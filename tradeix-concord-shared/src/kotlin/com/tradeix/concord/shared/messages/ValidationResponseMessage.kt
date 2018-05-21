package com.tradeix.concord.shared.messages

data class ValidationResponseMessage(val errors: Iterable<String>)
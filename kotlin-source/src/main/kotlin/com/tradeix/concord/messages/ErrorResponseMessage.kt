package com.tradeix.concord.messages

open class ErrorResponseMessage(
        override val correlationId: String,
        open val errorMessage: String
) : Message(correlationId)
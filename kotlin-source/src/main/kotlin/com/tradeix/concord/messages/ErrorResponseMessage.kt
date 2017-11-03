package com.tradeix.concord.messages

open class ErrorResponseMessage(
        override val correlationId: String,
        override val tryCount: Int,
        open val errorMessage: String
) : Message()
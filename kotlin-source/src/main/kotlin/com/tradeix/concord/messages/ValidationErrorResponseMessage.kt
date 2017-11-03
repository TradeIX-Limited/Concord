package com.tradeix.concord.messages

data class ValidationErrorResponseMessage(
        override val correlationId: String,
        override val tryCount: Int,
        override val errorMessage: String,
        val validationErrors: ArrayList<String>
) : ErrorResponseMessage(correlationId, 0, errorMessage)
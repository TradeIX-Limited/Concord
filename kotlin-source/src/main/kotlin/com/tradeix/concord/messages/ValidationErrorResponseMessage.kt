package com.tradeix.concord.messages

data class ValidationErrorResponseMessage(
        override val correlationId: String,
        override val errorMessage: String,
        val validationErrors: ArrayList<String>
) : ErrorResponseMessage(correlationId, errorMessage)
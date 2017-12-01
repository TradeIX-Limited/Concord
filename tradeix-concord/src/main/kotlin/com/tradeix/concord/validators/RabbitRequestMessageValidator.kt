package com.tradeix.concord.validators

import com.tradeix.concord.messages.rabbit.RabbitRequestMessage

class RabbitRequestMessageValidator(message: RabbitRequestMessage)
    : Validator<RabbitRequestMessage>(message) {
    companion object {
        private val EX_CORRELATION_ID_REQUIRED = "Field 'correlationId' is required."
        private val EX_TRY_COUNT_POSITIVE = "Field 'tryCount' cannot be negative."
    }

    override fun validate() {
        message.correlationId ?: errors.add(EX_CORRELATION_ID_REQUIRED)

        if (message.tryCount < 0) {
            errors.add(EX_TRY_COUNT_POSITIVE)
        }
    }
}
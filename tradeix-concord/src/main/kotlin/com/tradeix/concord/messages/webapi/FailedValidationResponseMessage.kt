package com.tradeix.concord.messages.webapi

import com.tradeix.concord.messages.MultiErrorMessage

class FailedValidationResponseMessage(
        override val errorMessages: List<String>
) : MultiErrorMessage
package com.tradeix.concord.messages.webapi

import com.tradeix.concord.messages.SingleErrorMessage

class FailedResponseMessage(
        override val errorMessage: String
) : SingleErrorMessage
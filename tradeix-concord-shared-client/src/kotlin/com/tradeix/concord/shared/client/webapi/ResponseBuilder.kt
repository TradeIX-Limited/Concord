package com.tradeix.concord.shared.client.webapi

import com.tradeix.concord.shared.messages.ErrorResponseMessage
import com.tradeix.concord.shared.messages.ValidationResponseMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ResponseBuilder {

    fun <T> ok(entity: T): ResponseEntity<T> {
        return ResponseEntity.ok(entity)
    }

    fun badRequest(errorMessage: String?): ResponseEntity<ErrorResponseMessage> {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ErrorResponseMessage(errorMessage ?: "Bad request."))
    }

    fun internalServerError(errorMessage: String?): ResponseEntity<ErrorResponseMessage> {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseMessage(errorMessage ?: "Internal server error."))
    }

    fun validationFailed(errors: Iterable<String>): ResponseEntity<ValidationResponseMessage> {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ValidationResponseMessage(errors))
    }
}
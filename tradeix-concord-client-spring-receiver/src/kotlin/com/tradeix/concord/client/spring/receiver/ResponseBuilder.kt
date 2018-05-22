package com.tradeix.concord.client.spring.receiver

import com.tradeix.concord.shared.messages.ErrorResponseMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ResponseBuilder {

    fun <T> ok(entity: T): ResponseEntity<T> {
        return ResponseEntity.ok(entity)
    }

    fun badRequest(errorMessage: String): ResponseEntity<ErrorResponseMessage> {
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(ErrorResponseMessage(errorMessage))
    }

    fun internalServerError(errorMessage: String?): ResponseEntity<ErrorResponseMessage> {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseMessage(errorMessage ?: "Unknown internal server error."))
    }
}
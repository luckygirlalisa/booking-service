package com.rennixing.order.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(PaymentRefundException::class)
    fun handlePaymentRefundException(ex: PaymentRefundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(ex.message), HttpStatus.SERVICE_UNAVAILABLE)
    }
}

class ErrorResponse(val message: String?)

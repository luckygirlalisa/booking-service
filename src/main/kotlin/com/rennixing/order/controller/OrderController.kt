package com.rennixing.order.controller

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.rennixing.order.controller.dto.OrderPaymentConfirmationRequestDto
import com.rennixing.order.controller.dto.PaymentConfirmationResponseDto
import com.rennixing.order.controller.dto.PaymentStatus
import com.rennixing.order.exception.OrderNotFoundException
import com.rennixing.order.exception.ZhifubaoConnectionException
import com.rennixing.order.service.ApplicationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest

@RestController
@RequestMapping("/travel-booking-orders")
class OrderController(
    private val applicationService: ApplicationService
) {

    @PostMapping("/{oid}/payment/confirmation")
    @ResponseStatus(HttpStatus.CREATED)
    fun pay(@PathVariable("oid") orderId: String,
            @RequestBody orderPaymentRequestDto: OrderPaymentConfirmationRequestDto
    ): ResponseEntity<PaymentConfirmationResponseDto> {
        try {
            applicationService.pay(orderId, orderPaymentRequestDto)
        } catch (exception: OrderNotFoundException) {
            return ResponseEntity(
                PaymentConfirmationResponseDto(PaymentStatus.FAILED, exception.message),
                HttpStatus.NOT_FOUND
            )
        } catch (exception: ZhifubaoConnectionException) {
            return ResponseEntity(
                PaymentConfirmationResponseDto(PaymentStatus.FAILED, exception.message),
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
        return ResponseEntity(PaymentConfirmationResponseDto(PaymentStatus.SUCCESS, null), HttpStatus.OK)
    }

    @ExceptionHandler(InvalidFormatException::class)
    fun handleConstraintViolation(
        ex: InvalidFormatException, request: WebRequest?
    ): ResponseEntity<Any?>? {
        return ResponseEntity(
            PaymentConfirmationResponseDto(PaymentStatus.FAILED, ex.message),
            HttpStatus.BAD_REQUEST
        )
    }
}

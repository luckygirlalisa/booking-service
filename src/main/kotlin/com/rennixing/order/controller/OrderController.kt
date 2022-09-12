package com.rennixing.order.controller

import com.rennixing.order.controller.dto.OrderPaymentConfirmationRequestDto
import com.rennixing.order.controller.dto.PaymentConfirmationResponseDto
import com.rennixing.order.controller.dto.PaymentStatus
import com.rennixing.order.exception.OrderNotFoundException
import com.rennixing.order.exception.PaymentTypeNotAcceptableException
import com.rennixing.order.exception.ZhifubaoConnectionException
import com.rennixing.order.service.ApplicationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler

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
//
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    fun createOrder(@RequestBody orderRequestDto: OrderRequestDto): Order {
//        return orderService.createOrder(orderRequestDto)
//    }
//
//    @GetMapping("/{oid}")
//    fun getOrderSummary(@PathVariable("oid") orderId: String): ResponseEntity<Order> {
//        return orderService.getOrderSummary(orderId)?.let {
//            ResponseEntity(it, HttpStatus.OK)
//        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
//    }
//
//    @PostMapping("/{oid}/cancellation")
//    fun cancellation(
//        @PathVariable("oid") orderId: String,
//        @RequestBody cancellationRequest: CancellationRequestDto
//    ): ResponseEntity<CancellationResponseDto> {
//        return orderService.applyCancellation(orderId, cancellationRequest.createAt)
//            ?.let {
//                ResponseEntity(CancellationResponseDto(it), HttpStatus.OK)
//            } ?: ResponseEntity(HttpStatus.CONFLICT)
//    }
//
//    @PostMapping("/{oid}/cancellation/confirmation")
//    fun cancellationConfirmation(
//        @PathVariable("oid") orderId: String,
//        @RequestBody confirmCancellationRequestDto: ConfirmCancellationRequestDto
//    ): ResponseEntity<ConfirmCancellationResponseDto> {
//        return orderService.confirmCancellation(orderId, confirmCancellationRequestDto.fulfilledAt)
//            ?.let {
//                ResponseEntity(ConfirmCancellationResponseDto(it), HttpStatus.OK)
//            } ?: ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE)
//    }
}

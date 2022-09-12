package com.rennixing.order.service

import com.rennixing.order.adaptor.payment.PaymentAdaptor
import com.rennixing.order.controller.dto.OrderPaymentConfirmationRequestDto
import com.rennixing.order.controller.dto.PaymentConfirmationResponseDto
import org.springframework.stereotype.Service

@Service
class ApplicationService(private val orderService: OrderService, private val paymentAdaptor: PaymentAdaptor) {
    fun pay(
        orderId: String,
        orderPaymentConfirmationRequestDto: OrderPaymentConfirmationRequestDto
    ): PaymentConfirmationResponseDto {
        val order = orderService.findOrder(orderId)

        val paymentResultFromZhifubao = paymentAdaptor.payWithZhifubao(order)

        val paymentStatus = orderService.pay(order, paymentResultFromZhifubao)

        return PaymentConfirmationResponseDto(paymentStatus, null)
    }
}

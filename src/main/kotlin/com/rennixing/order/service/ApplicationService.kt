package com.rennixing.order.service

import com.rennixing.order.adaptor.booking.AirTicketBookingAdaptor
import com.rennixing.order.adaptor.payment.PaymentAdaptor
import com.rennixing.order.controller.dto.OrderPaymentConfirmationRequestDto
import com.rennixing.order.controller.dto.PaymentConfirmationResponseDto
import com.rennixing.order.controller.dto.TicketCancelConfirmationResponseDto
import com.rennixing.order.exception.AirTicketBookingSystemConenctionException
import com.rennixing.order.exception.InvalidTicketTypeForCancellationException
import com.rennixing.order.model.TicketType
import org.springframework.stereotype.Service

@Service
class ApplicationService(
    private val orderService: OrderService,
    private val paymentAdaptor: PaymentAdaptor,
    private val bookingAdaptor: AirTicketBookingAdaptor
) {
    fun pay(
        orderId: String,
        orderPaymentConfirmationRequestDto: OrderPaymentConfirmationRequestDto
    ): PaymentConfirmationResponseDto {
        val order = orderService.findOrder(orderId)

        val paymentResultFromZhifubao = paymentAdaptor.payWithZhifubao(order)

        val paymentStatus = orderService.pay(order, paymentResultFromZhifubao)

        return PaymentConfirmationResponseDto(paymentStatus, null)
    }

    fun cancelTicket(orderId: String): TicketCancelConfirmationResponseDto {
        val order = orderService.findOrder(orderId)

        if (!order.ticketType.equals(TicketType.AIR)) {
            throw InvalidTicketTypeForCancellationException("Only air ticket can be cancelled.")
        }
        val cancelTicketResponse =
            try {
                bookingAdaptor.cancelTicket(order)
            } catch (exception: AirTicketBookingSystemConenctionException) {
                null
            }

        val cancelTicketStatus = orderService.cancelTicket(order, cancelTicketResponse)

        val message = if (cancelTicketResponse == null) AirTicketBookingSystemConenctionException().message else null
        return TicketCancelConfirmationResponseDto(cancelTicketStatus, message)
    }
}

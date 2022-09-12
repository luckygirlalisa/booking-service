package com.rennixing.order.model

import com.rennixing.order.controller.dto.TicketCancellationStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "order")
class Order(
    @Id
    val id: String,
    val userId: String,
    val price: Double,
    var paymentFulfillment: PaymentFulfillment?,
    val ticketType: TicketType,
    var ticketCancellationStatus: TicketCancellationStatus?,
    var ticketCancellationFulfillment: TicketCancellationFulfillment?
) {
    fun applyPayment() {
        paymentFulfillment = PaymentFulfillment(LocalDateTime.now().toString(), price)
    }

    fun confirmTicketCancellation() {
        ticketCancellationStatus = TicketCancellationStatus.SUCCESS
        ticketCancellationFulfillment = TicketCancellationFulfillment(LocalDateTime.now().toString())
    }

    fun ticketCancellationFailed() {
        ticketCancellationStatus = TicketCancellationStatus.FAILED
    }
}

class PaymentFulfillment(
    val createAt: String,
    var amount: Double
)

class TicketCancellationFulfillment(
    val createAt: String
)

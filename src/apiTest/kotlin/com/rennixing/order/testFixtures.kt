package com.rennixing.order

import com.rennixing.order.model.Order
import com.rennixing.order.model.PaymentFulfillment
import com.rennixing.order.model.TicketType

val orderWaitingForPayment = Order(
    id = "orderWaitingForPayment",
    userId = "userId",
    price = 22.00,
    paymentFulfillment = PaymentFulfillment(
        createAt = "2021-09-11T23:38:00",
        amount = 22.00
    ),
    ticketType = TicketType.AIR,
    ticketCancellationStatus = null,
    ticketCancellationFulfillment = null
)

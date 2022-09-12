package com.rennixing.order.model

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
) {
    fun applyPayment() {
        paymentFulfillment = PaymentFulfillment(LocalDateTime.now().toString(), price)
    }
}

class PaymentFulfillment(
    val createAt: String,
    var amount: Double
)

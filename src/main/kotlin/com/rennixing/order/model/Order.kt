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
//    val merchantId: String,
//    val orderItems: List<OrderItem>,
//    var state: OrderState = OrderState.CREATED,
//    var paymentFulfilment: PaymentFulfilment? = null,
//    var orderAcceptFulfilment: OrderAcceptFulfilment? = null,
//    var prepareMealFulfilment: PrepareMealFulfilment? = null,
//    var orderCancellationFulfilment: OrderCancellationFulfilment? = null
) {
    fun applyPayment() {
        paymentFulfillment = PaymentFulfillment(LocalDateTime.now().toString(), price)
    }
//    constructor(orderRequestDto: OrderRequestDto) : this(
//        id = ObjectId().toString(),
//        userId = orderRequestDto.userId,
//        merchantId = orderRequestDto.merchantId,
//        orderItems = orderRequestDto.orderItems
//    )
//
//    val minutesToPrepareOrder get() = orderItems.maxOf { it.minutesToPrepare }
//
//    fun applyCancellation(createAt: String): String? {
//        val cancellationCreatedAt = createAt.toLocalDateTime()
//        val prepareMealExpireAt = prepareMealFulfilment?.expireAt?.toLocalDateTime()
//        val isPreparingMeal = state == OrderState.PREP_MEAL_FULFILMENT_CREATED
//        val prepMealExpired = cancellationCreatedAt.isAfter(prepareMealExpireAt)
//
//        return if (isPreparingMeal && prepMealExpired) {
//            val expireAt = cancellationCreatedAt.plusMinutes(5).toISOLocalDateTime()
//            this.orderCancellationFulfilment = OrderCancellationFulfilment(
//                createAt = cancellationCreatedAt.toISOLocalDateTime(),
//                expireAt = cancellationCreatedAt.plusMinutes(5).toISOLocalDateTime(),
//                fulfilledAt = null
//            )
//            this.state = OrderState.CANCELLATION_FULFILMENT_CREATED
//
//            expireAt
//        } else {
//            null
//        }
//    }
//
//    fun confirmCancellation(fulfilledAt: String): String? {
//        val isPendingCancellation = state == OrderState.CANCELLATION_FULFILMENT_CREATED
//
//        return if (isPendingCancellation) {
//            this.orderCancellationFulfilment?.fulfilledAt = fulfilledAt
//            this.state = OrderState.CANCELLED
//
//            fulfilledAt
//        } else {
//            null
//        }
//    }
}

class PaymentFulfillment(
    val createAt: String,
    var amount: Double
)

//class PaymentFulfilment(
//    val createAt: String,
//    val expireAt: String,
//    val amount: Double,
//    var fulfilledAt: String? = null,
//    var payOrderId: String? = null,
//    var redPacketId: String? = null,
//)

class OrderAcceptFulfilment(
    val createAt: String,
    val expireAt: String,
    var fulfilledAt: String? = null,
)

class PrepareMealFulfilment(
    val createAt: String,
    val expireAt: String,
    var fulfilledAt: String? = null,
)

class OrderCancellationFulfilment(
    val createAt: String,
    val expireAt: String,
    var fulfilledAt: String? = null,
)

class OrderItem(
    val productId: String,
    val price: Double,
    val minutesToPrepare: Long
)

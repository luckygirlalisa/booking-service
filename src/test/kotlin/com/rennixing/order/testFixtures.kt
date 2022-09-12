package com.rennixing.order

import com.rennixing.order.model.Order

val orderStillInPrepPeriod = Order(
    id = "orderStillInPrepPeriod",
    userId = "userId",
    price = 12.00,
    paymentFulfillment = null,
//    merchantId = "merchantId",
//    orderItems = listOf(
//        OrderItem(productId = "product1", price = 10.0, minutesToPrepare = 30)
//    ),
//    state = OrderState.PREP_MEAL_FULFILMENT_CREATED,
//    paymentFulfilment = PaymentFulfilment(
//        createAt = "2021-09-11T23:38:00",
//        expireAt = "2021-09-11T23:43:00",
//        amount = 10.0,
//        fulfilledAt = "2021-09-11T23:40:00",
//        payOrderId = "oid_success123",
//        redPacketId = "rid"
//    ),
//    orderAcceptFulfilment = OrderAcceptFulfilment(
//        createAt = "2021-09-11T23:40:10",
//        expireAt = "2021-09-11T23:50:10",
//        fulfilledAt = "2021-09-11T23:46:00"
//    ),
//    prepareMealFulfilment = PrepareMealFulfilment(
//        createAt = "2021-09-11T23:46:00",
//        expireAt = "2021-09-12T00:16:00",
//        fulfilledAt = null
//    )
)
//
//val orderStillInPrepPeriod2 = Order(
//    id = "orderStillInPrepPeriod2",
//    userId = "userId",
//    merchantId = "merchantId",
//    orderItems = listOf(
//        OrderItem(productId = "product1", price = 10.0, minutesToPrepare = 30),
//        OrderItem(productId = "product2", price = 10.0, minutesToPrepare = 5)
//    ),
//    state = OrderState.PREP_MEAL_FULFILMENT_CREATED,
//    paymentFulfilment = PaymentFulfilment(
//        createAt = "2021-09-11T23:38:00",
//        expireAt = "2021-09-11T23:43:00",
//        amount = 10.0,
//        fulfilledAt = "2021-09-11T23:40:00",
//        payOrderId = "oid_success456",
//        redPacketId = "rid"
//    ),
//    orderAcceptFulfilment = OrderAcceptFulfilment(
//        createAt = "2021-09-11T23:40:10",
//        expireAt = "2021-09-11T23:50:10",
//        fulfilledAt = "2021-09-11T23:46:00"
//    ),
//    prepareMealFulfilment = PrepareMealFulfilment(
//        createAt = "2021-09-11T23:46:00",
//        expireAt = "2021-09-12T00:16:00",
//        fulfilledAt = null
//    )
//)
//
//val orderPendingCancellationConfirmation = Order(
//    id = "orderPendingCancellationConfirmation",
//    userId = "userId",
//    merchantId = "merchantId",
//    orderItems = listOf(
//        OrderItem(productId = "product1", price = 10.0, minutesToPrepare = 30),
//        OrderItem(productId = "product2", price = 10.0, minutesToPrepare = 5)
//    ),
//    state = OrderState.CANCELLATION_FULFILMENT_CREATED,
//    paymentFulfilment = PaymentFulfilment(
//        createAt = "2021-09-11T23:38:00",
//        expireAt = "2021-09-11T23:43:00",
//        amount = 10.0,
//        fulfilledAt = "2021-09-11T23:40:00",
//        payOrderId = "oid_success456",
//        redPacketId = "rid"
//    ),
//    orderAcceptFulfilment = OrderAcceptFulfilment(
//        createAt = "2021-09-11T23:40:10",
//        expireAt = "2021-09-11T23:50:10",
//        fulfilledAt = "2021-09-11T23:46:00"
//    ),
//    prepareMealFulfilment = PrepareMealFulfilment(
//        createAt = "2021-09-11T23:46:00",
//        expireAt = "2021-09-12T00:16:00",
//        fulfilledAt = null
//    ),
//    orderCancellationFulfilment = OrderCancellationFulfilment(
//        createAt = "2021-09-12T00:20:00",
//        expireAt = "2021-09-12T00:25:00",
//        fulfilledAt = null
//    )
//)
//
//val orderPendingCancellationConfirmation2 = Order(
//    id = "orderPendingCancellationConfirmation2",
//    userId = "userId",
//    merchantId = "merchantId",
//    orderItems = listOf(
//        OrderItem(productId = "product1", price = 10.0, minutesToPrepare = 30),
//        OrderItem(productId = "product2", price = 10.0, minutesToPrepare = 5)
//    ),
//    state = OrderState.CANCELLATION_FULFILMENT_CREATED,
//    paymentFulfilment = PaymentFulfilment(
//        createAt = "2021-09-11T23:38:00",
//        expireAt = "2021-09-11T23:43:00",
//        amount = 10.0,
//        fulfilledAt = "2021-09-11T23:40:00",
//        payOrderId = "oid_fail123",
//        redPacketId = "rid"
//    ),
//    orderAcceptFulfilment = OrderAcceptFulfilment(
//        createAt = "2021-09-11T23:40:10",
//        expireAt = "2021-09-11T23:50:10",
//        fulfilledAt = "2021-09-11T23:46:00"
//    ),
//    prepareMealFulfilment = PrepareMealFulfilment(
//        createAt = "2021-09-11T23:46:00",
//        expireAt = "2021-09-12T00:16:00",
//        fulfilledAt = null
//    ),
//    orderCancellationFulfilment = OrderCancellationFulfilment(
//        createAt = "2021-09-12T00:20:00",
//        expireAt = "2021-09-12T00:25:00",
//        fulfilledAt = null
//    )
//)
//
//val orderPendingCancellationConfirmation3 = Order(
//    id = "orderPendingCancellationConfirmation3",
//    userId = "userId",
//    merchantId = "merchantId",
//    orderItems = listOf(
//        OrderItem(productId = "product1", price = 10.0, minutesToPrepare = 30),
//        OrderItem(productId = "product2", price = 10.0, minutesToPrepare = 5)
//    ),
//    state = OrderState.CANCELLATION_FULFILMENT_CREATED,
//    paymentFulfilment = PaymentFulfilment(
//        createAt = "2021-09-11T23:38:00",
//        expireAt = "2021-09-11T23:43:00",
//        amount = 10.0,
//        fulfilledAt = "2021-09-11T23:40:00",
//        payOrderId = "oid_exception123",
//        redPacketId = "rid"
//    ),
//    orderAcceptFulfilment = OrderAcceptFulfilment(
//        createAt = "2021-09-11T23:40:10",
//        expireAt = "2021-09-11T23:50:10",
//        fulfilledAt = "2021-09-11T23:46:00"
//    ),
//    prepareMealFulfilment = PrepareMealFulfilment(
//        createAt = "2021-09-11T23:46:00",
//        expireAt = "2021-09-12T00:16:00",
//        fulfilledAt = null
//    ),
//    orderCancellationFulfilment = OrderCancellationFulfilment(
//        createAt = "2021-09-12T00:20:00",
//        expireAt = "2021-09-12T00:25:00",
//        fulfilledAt = null
//    )
//)
//
//val orderPrepMealFulfilled = Order(
//    id = "orderPrepMealFulfilled",
//    userId = "userId",
//    merchantId = "merchantId",
//    orderItems = listOf(
//        OrderItem(productId = "product1", price = 10.0, minutesToPrepare = 30)
//    ),
//    state = OrderState.MEAL_PREPARED,
//    paymentFulfilment = PaymentFulfilment(
//        createAt = "2021-09-11T23:38:00",
//        expireAt = "2021-09-11T23:43:00",
//        amount = 10.0,
//        fulfilledAt = "2021-09-11T23:40:00",
//        payOrderId = "oid_success123",
//        redPacketId = "rid"
//    ),
//    orderAcceptFulfilment = OrderAcceptFulfilment(
//        createAt = "2021-09-11T23:40:10",
//        expireAt = "2021-09-11T23:50:10",
//        fulfilledAt = "2021-09-11T23:46:00"
//    ),
//    prepareMealFulfilment = PrepareMealFulfilment(
//        createAt = "2021-09-11T23:46:00",
//        expireAt = "2021-09-12T00:16:00",
//        fulfilledAt = "2021-09-12T00:01:00"
//    )
//)
//
//val orderPrepMealFulfilled2 = Order(
//    id = "orderPrepMealFulfilled2",
//    userId = "userId",
//    merchantId = "merchantId",
//    orderItems = listOf(
//        OrderItem(productId = "product1", price = 10.0, minutesToPrepare = 5),
//        OrderItem(productId = "product2", price = 10.0, minutesToPrepare = 10)
//    ),
//    state = OrderState.MEAL_PREPARED,
//    paymentFulfilment = PaymentFulfilment(
//        createAt = "2021-09-11T23:38:00",
//        expireAt = "2021-09-11T23:43:00",
//        amount = 20.0,
//        fulfilledAt = "2021-09-11T23:40:00",
//        payOrderId = "oid_success123",
//        redPacketId = "rid"
//    ),
//    orderAcceptFulfilment = OrderAcceptFulfilment(
//        createAt = "2021-09-11T23:40:10",
//        expireAt = "2021-09-11T23:50:10",
//        fulfilledAt = "2021-09-11T23:46:00"
//    ),
//    prepareMealFulfilment = PrepareMealFulfilment(
//        createAt = "2021-09-11T23:46:00",
//        expireAt = "2021-09-11T23:56:00",
//        fulfilledAt = "2021-09-12T00:01:00"
//    )
//)

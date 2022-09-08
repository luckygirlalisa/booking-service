package com.rennixing.order.model

enum class OrderState {
    CREATED,
    PAYMENT_FULFILMENT_CREATED,
    PAID,
    ORDER_ACCEPT_FULFILMENT_CREATED,
    ACCEPTED,
    PREP_MEAL_FULFILMENT_CREATED,
    MEAL_PREPARED,
    CANCELLATION_FULFILMENT_CREATED,
    CANCELLED
}

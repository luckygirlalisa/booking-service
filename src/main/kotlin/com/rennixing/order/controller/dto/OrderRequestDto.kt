package com.rennixing.order.controller.dto

import com.rennixing.order.model.OrderItem

data class OrderRequestDto(
    val userId: String,
    val merchantId: String,
    val orderItems: List<OrderItem>
)

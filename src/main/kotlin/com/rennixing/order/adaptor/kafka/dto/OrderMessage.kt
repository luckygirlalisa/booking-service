package com.rennixing.order.adaptor.kafka.dto

data class OrderMessage(
    val type: String,
    val metadata: String
)

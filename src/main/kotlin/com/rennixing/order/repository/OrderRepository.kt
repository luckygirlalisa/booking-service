package com.rennixing.order.repository

import com.rennixing.order.model.Order
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class OrderRepository(
    private val mongoTemplate: MongoTemplate
) {
    fun save(order: Order): Order {
        return mongoTemplate.save(order)
    }

    fun findById(id: String): Order? {
        return mongoTemplate.findById(id, Order::class.java)
    }
}

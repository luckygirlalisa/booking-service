package com.rennixing.order.repository

import com.rennixing.order.model.Order
import com.rennixing.order.model.PaymentFulfillment
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate

@ExtendWith(MockKExtension::class)
@DataMongoTest
@Import(OrderRepository::class)
internal class OrderRepositoryTest {
    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Test
    fun shouldSaveOrderWithPaymentFulfilmentSuccessful() {
        val savedOrder = Order(
            id = "orderId",
            userId = "userId",
            price = 12.00,
            paymentFulfillment = PaymentFulfillment(
                createAt = "2021-09-11T23:38:00",
                amount = 10.0,
            )
        )

        val result = orderRepository.save(savedOrder)

        assertEquals(savedOrder.id, result.id)
        assertEquals(savedOrder.userId, result.userId)
        assertEquals(savedOrder.paymentFulfillment, result.paymentFulfillment)
    }

    @Test
    internal fun shouldFindOrderWithOrderId() {
        val orderId = "orderId"
        val existingOrder = Order(
            id = orderId,
            userId = "userId",
            price = 12.00,
            paymentFulfillment = PaymentFulfillment(
                createAt = "2021-09-11T23:38:00",
                amount = 10.0,
            )
        )
        orderRepository.save(existingOrder)

        val foundOrder = orderRepository.findById(orderId)

        assertEquals(existingOrder.id, foundOrder!!.id)
        assertEquals(existingOrder.userId, foundOrder.userId)
        assertEquals(existingOrder.price, foundOrder.price)
        assertEquals(existingOrder.paymentFulfillment!!.amount, foundOrder.paymentFulfillment!!.amount)
    }

    @Test
    internal fun shouldReturnNullWhenOrderNotFound() {
        val orderId = "not-exist-orderId"
        val foundOrder = orderRepository.findById(orderId)

        assertNull(foundOrder)
    }

}

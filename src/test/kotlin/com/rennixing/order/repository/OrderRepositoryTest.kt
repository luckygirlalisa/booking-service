package com.rennixing.order.repository

import com.rennixing.order.model.Order
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
    fun `should save order to database and return saved item`() {
        val savedOrder = Order(
            id = "orderId",
            userId = "userId",
            price = 12.00,
            paymentFulfillment = null,
//            merchantId = "merchantId",
//            orderItems = listOf(
//                OrderItem(productId = "product1", price = 15.0, minutesToPrepare = 30),
//            ),
//            state = OrderState.ACCEPTED,
//            paymentFulfilment = PaymentFulfilment(
//                createAt = "2021-09-11T23:38:00",
//                expireAt = "2021-09-11T23:43:00",
//                amount = 10.0,
//                fulfilledAt = "2021-09-11T23:40:00",
//                payOrderId = "oid_success123",
//                redPacketId = "rid"
//            )
        )

        val result = orderRepository.save(savedOrder)

        assertEquals(savedOrder.id, result.id)
        assertEquals(savedOrder.userId, result.userId)
//        assertEquals(savedOrder.merchantId, result.merchantId)
    }
//
//    @Test
//    fun `should retrieve order from database and return retrieved item`() {
//        val savedOrder = Order(
//            id = "orderId",
//            userId = "userId",
//            merchantId = "merchantId",
//            orderItems = listOf(
//                OrderItem(productId = "product1", price = 15.0, minutesToPrepare = 40),
//                OrderItem(productId = "product2", price = 5.0, minutesToPrepare = 10)
//            )
//        )
//        mongoTemplate.save(savedOrder)
//
//        val found = orderRepository.findById("orderId")
//        assertEquals(savedOrder.id, found!!.id)
//        assertEquals(savedOrder.userId, found.userId)
//        assertEquals(savedOrder.merchantId, found.merchantId)
//        assertEquals(OrderState.CREATED, found.state)
//    }

    @Test
    fun `should return null given order not exist in database`() {
        assertNull(orderRepository.findById("orderIdNotExist"))
    }
}

package com.rennixing.order.service

import com.rennixing.order.adaptor.apiclient.PaymentFeignClient
import com.rennixing.order.adaptor.kafka.MessageProducer
import com.rennixing.order.repository.OrderRepository
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class OrderServiceTest {
    @MockK
    private lateinit var orderRepository: OrderRepository

    @MockK(relaxed = true)
    private lateinit var messageProducer: MessageProducer

    @MockK
    private lateinit var paymentFeignClient: PaymentFeignClient

    private lateinit var orderService: OrderService

    @BeforeEach
    fun setUp() {
        orderService = OrderService(
            orderRepository = orderRepository,
            paymentFeignClient = paymentFeignClient,
            messageProducer = messageProducer
        )
    }
//
//    @Test
//    fun `should create a new order and save to repository when calling createOrder`() {
//        val newOrderRequest = OrderRequestDto(
//            userId = "userId",
//            merchantId = "merchantId",
//            orderItems = listOf(
//                OrderItem(productId = "product1", price = 15.0, minutesToPrepare = 40),
//                OrderItem(productId = "product2", price = 5.0, minutesToPrepare = 10)
//            )
//        )
//        every { orderRepository.save(any()) } returns Order(
//            id = "orderId",
//            userId = "userId",
//            merchantId = "merchantId",
//            orderItems = listOf(
//                OrderItem(productId = "product1", price = 15.0, minutesToPrepare = 40),
//                OrderItem(productId = "product2", price = 5.0, minutesToPrepare = 10)
//            )
//        )
//
//        val savedOrder = orderService.createOrder(newOrderRequest)
//
//        assertNotNull(savedOrder.id)
//        assertEquals("userId", savedOrder.userId)
//        assertEquals("merchantId", savedOrder.merchantId)
//        assertEquals(2, savedOrder.orderItems.size)
//    }
//
//    @Test
//    fun `should get order from repository by using orderId when calling getOrderSummary`() {
//        val orderId = "orderInDB"
//        every { orderRepository.findById(orderId) } returns Order(
//            id = "orderInDB",
//            userId = "userId",
//            merchantId = "merchantId",
//            orderItems = listOf(
//                OrderItem(productId = "product1", price = 15.0, minutesToPrepare = 40),
//                OrderItem(productId = "product2", price = 5.0, minutesToPrepare = 10)
//            )
//        )
//
//        val orderSummary = orderService.getOrderSummary(orderId)
//
//        assertEquals(orderId, orderSummary!!.id)
//        assertEquals("userId", orderSummary.userId)
//        assertEquals("merchantId", orderSummary.merchantId)
//        assertEquals(2, orderSummary.orderItems.size)
//    }
//
//    @Test
//    fun `should get a null response from repository when getOrderSummary given order does not exist`() {
//        val orderId = "orderNotInDB"
//        every { orderRepository.findById(orderId) } returns null
//
//        val orderSummary = orderService.getOrderSummary(orderId)
//
//        assertNull(orderSummary)
//    }
//
//    @Test
//    fun `should fail to apply cancellation when order is still in meal preparation period`() {
//        val orderId = "orderStillInPrepPeriod"
//        val createAt = "2021-09-12T00:06:00"
//        every { orderRepository.findById(orderId) } returns orderStillInPrepPeriod
//
//        val fulfilmentExpireAt = orderService.applyCancellation(orderId, createAt)
//
//        assertNull(fulfilmentExpireAt)
//    }
//
//    @Test
//    fun `should fail to apply cancellation when a multi-item order is still in meal preparation period`() {
//        val orderId = "orderStillInPrepPeriod2"
//        val createAt = "2021-09-12T00:06:00"
//        every { orderRepository.findById(orderId) } returns orderStillInPrepPeriod2
//
//        val fulfilmentExpireAt = orderService.applyCancellation(orderId, createAt)
//
//        assertNull(fulfilmentExpireAt)
//    }
//
//    @Test
//    fun `should apply cancellation successfully when a multi-item order exceed the preparation period`() {
//        val orderId = "orderStillInPrepPeriod2"
//        val createAt = "2021-09-12T00:20:00"
//        every { orderRepository.findById(orderId) } returns orderStillInPrepPeriod2
//        every { orderRepository.save(any()) } returns orderStillInPrepPeriod2
//
//        val fulfilmentExpireAt = orderService.applyCancellation(orderId, createAt)
//
//        assertEquals("2021-09-12T00:25:00", fulfilmentExpireAt)
//        verify(exactly = 1) {
//            orderRepository.save(
//                withArg {
//                    assertEquals("2021-09-12T00:25:00", it.orderCancellationFulfilment?.expireAt)
//                    assertEquals(OrderState.CANCELLATION_FULFILMENT_CREATED, it.state)
//                }
//            )
//        }
//    }
//
//    @Test
//    fun `should confirm cancel order when payment refund are done successfully`() {
//        val orderId = "orderPendingCancellationConfirmation"
//        val payOrderId = "oid_success456"
//        val redPacketId = "rid"
//        val fulfilledAt = "2021-09-12 00:23:00"
//        every { orderRepository.findById(orderId) } returns orderPendingCancellationConfirmation
//        every { orderRepository.save(any()) } returns orderStillInPrepPeriod2
//        every { paymentFeignClient.refundPayOrder(payOrderId) } returns RefundResponseDto(refundStatus = "success")
//
//        val resultFulfilledAt = orderService.confirmCancellation(orderId, fulfilledAt)
//
//        assertEquals(fulfilledAt, resultFulfilledAt)
//        verify(exactly = 1) {
//            messageProducer.sendReturnRedPacketMessage(redPacketId)
//            orderRepository.save(
//                withArg {
//                    assertEquals(fulfilledAt, it.orderCancellationFulfilment?.fulfilledAt)
//                    assertEquals(OrderState.CANCELLED, it.state)
//                }
//            )
//        }
//    }
//
//    @Test
//    fun `should not change order state when payment refund was not success`() {
//        val orderId = "orderPendingCancellationConfirmation2"
//        val payOrderId = "oid_fail123"
//        val fulfilledAt = "2021-09-12 00:23:00"
//
//        every { orderRepository.findById(orderId) } returns orderPendingCancellationConfirmation2
//        every { orderRepository.save(any()) } returns orderStillInPrepPeriod2
//        every { paymentFeignClient.refundPayOrder(payOrderId) } returns RefundResponseDto(refundStatus = "fail")
//
//        val resultFulfilledAt = orderService.confirmCancellation(orderId, fulfilledAt)
//
//        assertNull(resultFulfilledAt)
//        verify(exactly = 0) { messageProducer.sendReturnRedPacketMessage(any()) }
//        verify(exactly = 0) { orderRepository.save(any()) }
//    }
//
//    @Test
//    fun `should not change order state when payment exception happens`() {
//        val orderId = "orderPendingCancellationConfirmation3"
//        val payOrderId = "oid_exception123"
//        val fulfilledAt = "2021-09-12 00:23:00"
//
//        every { orderRepository.findById(orderId) } returns orderPendingCancellationConfirmation3
//        every { orderRepository.save(any()) } returns orderStillInPrepPeriod2
//        every { paymentFeignClient.refundPayOrder(payOrderId) } throws PaymentRefundException("exception message")
//
//        assertThrows<PaymentRefundException> { orderService.confirmCancellation(orderId, fulfilledAt) }
//        verify(exactly = 0) { messageProducer.sendReturnRedPacketMessage(any()) }
//        verify(exactly = 0) { orderRepository.save(any()) }
//    }
//
//    @Test
//    fun `should not confirm cancellation when orderId cannot be found in database`() {
//        val orderId = "orderDoesNotExist"
//        val fulfilledAt = "2021-09-12 00:23:00"
//        every { orderRepository.findById(orderId) } returns orderPendingCancellationConfirmation3
//
//        val resultFulfilledAt = orderService.confirmCancellation(orderId, fulfilledAt)
//
//        assertNull(resultFulfilledAt)
//    }
//
//    @Test
//    fun `should fail to apply cancellation when meal is already prepared`() {
//        val orderId = "orderPrepMealFulfilled"
//        val createAt = "2021-09-12T00:02:00"
//        every { orderRepository.findById(orderId) } returns orderPrepMealFulfilled
//
//        val fulfilmentExpireAt = orderService.applyCancellation(orderId, createAt)
//
//        assertNull(fulfilmentExpireAt)
//    }
//
//    @Test
//    fun `should fail to apply cancellation when meal is already prepared even the fulfillment was late`() {
//        val orderId = "orderPrepMealFulfilled2"
//        val createAt = "2021-09-12T00:02:00"
//        every { orderRepository.findById(orderId) } returns orderPrepMealFulfilled2
//
//        val fulfilmentExpireAt = orderService.applyCancellation(orderId, createAt)
//
//        assertNull(fulfilmentExpireAt)
//    }
//
//    @Test
//    fun `should fail to apply cancellation when provided orderId does not exist`() {
//        val orderId = "orderDoesNotExist"
//        val createAt = "2021-09-12T00:02:00"
//        every { orderRepository.findById(orderId) } returns null
//
//        val fulfilmentExpireAt = orderService.applyCancellation(orderId, createAt)
//
//        assertNull(fulfilmentExpireAt)
//    }
}

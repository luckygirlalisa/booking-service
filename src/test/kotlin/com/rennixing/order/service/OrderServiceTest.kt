package com.rennixing.order.service

import com.rennixing.order.adaptor.booking.TicketCancelResponseFrom3rdParty
import com.rennixing.order.adaptor.payment.PaymentResponseFromZhifubao
import com.rennixing.order.controller.dto.PaymentStatus
import com.rennixing.order.controller.dto.TicketCancellationStatus
import com.rennixing.order.exception.OrderNotFoundException
import com.rennixing.order.model.Order
import com.rennixing.order.model.TicketType
import com.rennixing.order.repository.OrderRepository
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class OrderServiceTest {
    @MockK
    private lateinit var orderRepository: OrderRepository

    private lateinit var orderService: OrderService

    @BeforeEach
    fun setUp() {
        orderService = OrderService(
            orderRepository = orderRepository
        )
    }

    @Test
    internal fun shouldReturnOrderInfoSuccessfulWithExistOrderId() {
        val orderId = "123"
        val userId = "abc"
        val order = Order(orderId, userId, 12.00, paymentFulfillment = null, TicketType.AIR,null, null)
        every {
            orderRepository.findById(orderId)
        } returns order

        val result = orderService.findOrder(orderId)

        assertEquals(order.id, result.id)
        assertEquals(order.userId, result.userId)
        assertEquals(order.paymentFulfillment, result.paymentFulfillment)
    }

    @Test
    internal fun shouldThrowOrderNotFoundExceptionWhenOrderIdNotExist() {
        every { orderRepository.findById("123") } returns null

        assertThrows(OrderNotFoundException::class.java) { orderService.findOrder("123") }
    }

    @Test
    internal fun shouldCreatePaymentFulfillmentSuccessForExistingOrder() {
        val orderId = "123"
        val userId = "abc"
        val order = Order(orderId, userId, price = 12.00, paymentFulfillment = null, TicketType.AIR,null, null)
        every { orderRepository.save(order) } returns order

        val paymentStatus = orderService.pay(order, PaymentResponseFromZhifubao(PaymentStatus.SUCCESS))

        assertEquals(PaymentStatus.SUCCESS, paymentStatus)
        assertNotNull(order.paymentFulfillment)
    }

    @Test
    internal fun shouldReturnCancelTicketSuccessWhenBookingAdaptorResponseIsNullButStoreCancelFailed() {
        val orderId = "123"
        val userId = "abc"
        val order = Order(orderId, userId, price = 12.00, paymentFulfillment = null, TicketType.AIR,null, null)
        every { orderRepository.save(order) } returns order

        val cancelTicketResult = orderService.cancelTicket(order, null)

        assertEquals(TicketCancellationStatus.SUCCESS, cancelTicketResult)
        assertEquals(TicketCancellationStatus.FAILED, order.ticketCancellationStatus)
        assertNull(order.ticketCancellationFulfillment)
    }

    @Test
    internal fun shouldReturnCancelTicketSuccessWhenBookingAdaptorResponseIsNotNull() {
        val orderId = "123"
        val userId = "abc"
        val order = Order(orderId, userId, price = 12.00, paymentFulfillment = null, TicketType.AIR, null, null)
        every { orderRepository.save(order) } returns order

        val cancelTicketResult = orderService.cancelTicket(order, TicketCancelResponseFrom3rdParty(TicketCancellationStatus.SUCCESS))

        assertEquals(TicketCancellationStatus.SUCCESS, cancelTicketResult)
        assertEquals(TicketCancellationStatus.SUCCESS, order.ticketCancellationStatus)
        assertNotNull(order.ticketCancellationFulfillment)
    }
}

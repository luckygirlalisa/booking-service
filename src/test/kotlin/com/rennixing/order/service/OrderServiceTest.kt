package com.rennixing.order.service

import com.rennixing.order.adaptor.booking.TicketCancelResponseFrom3rdParty
import com.rennixing.order.adaptor.payment.PaymentResponseFromZhifubao
import com.rennixing.order.controller.dto.PaymentStatus
import com.rennixing.order.controller.dto.TicketCancellationStatus
import com.rennixing.order.controller.dto.TicketCancellationStatus.FAILED
import com.rennixing.order.controller.dto.TicketCancellationStatus.SUCCESS
import com.rennixing.order.exception.OrderNotFoundException
import com.rennixing.order.model.Order
import com.rennixing.order.model.TicketCancellationFulfillment
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

        assertEquals(SUCCESS, cancelTicketResult)
        assertEquals(TicketCancellationStatus.FAILED, order.ticketCancellationStatus)
        assertNull(order.ticketCancellationFulfillment)
    }

    @Test
    internal fun shouldReturnCancelTicketSuccessWhenBookingAdaptorResponseIsNotNull() {
        val orderId = "123"
        val userId = "abc"
        val order = Order(orderId, userId, price = 12.00, paymentFulfillment = null, TicketType.AIR, null, null)
        every { orderRepository.save(order) } returns order

        val cancelTicketResult = orderService.cancelTicket(order, TicketCancelResponseFrom3rdParty(SUCCESS))

        assertEquals(SUCCESS, cancelTicketResult)
        assertEquals(SUCCESS, order.ticketCancellationStatus)
        assertNotNull(order.ticketCancellationFulfillment)
    }

    @Test
    internal fun shouldNotReturnCancelledOrderWhenFindAllCancellationFailedOnes() {
        val orderId = "123"
        val userId = "abc"
        val notCancelledOrder = Order(orderId, userId, price = 12.00, paymentFulfillment = null, TicketType.AIR, null, null)
        val idOfCanceledOrder = "456"
        val canceledOrder = Order(idOfCanceledOrder, userId, price = 12.00, paymentFulfillment = null, TicketType.AIR, SUCCESS, TicketCancellationFulfillment("2022-10-10"))
        val idOfCancellationFailedOrder = "789"
        val cancellationFailedOrder = Order(idOfCancellationFailedOrder, userId, price = 12.00, paymentFulfillment = null, TicketType.AIR, FAILED, null)
        every { orderRepository.findAll() } returns listOf(notCancelledOrder, canceledOrder, cancellationFailedOrder)

        val allFailedCancellationOrders = orderService.findFailedCancellationOrders()

        assertEquals(1, allFailedCancellationOrders.size)
        assertEquals(idOfCancellationFailedOrder, allFailedCancellationOrders.get(0).id)
    }
}

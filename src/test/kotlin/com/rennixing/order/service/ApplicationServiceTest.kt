package com.rennixing.order.service

import com.rennixing.order.adaptor.payment.PaymentAdaptor
import com.rennixing.order.adaptor.payment.PaymentResponseFromZhifubao
import com.rennixing.order.controller.dto.OrderPaymentConfirmationRequestDto
import com.rennixing.order.controller.dto.PaymentConfirmationResponseDto
import com.rennixing.order.controller.dto.PaymentStatus
import com.rennixing.order.controller.dto.PaymentType
import com.rennixing.order.exception.OrderNotFoundException
import com.rennixing.order.exception.ZhifubaoConnectionException
import com.rennixing.order.model.Order
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ApplicationServiceTest {
    @MockK
    private lateinit var orderService: OrderService

    @MockK
    private lateinit var paymentAdaptor: PaymentAdaptor

    private lateinit var applicationService: ApplicationService

    @BeforeEach
    fun setUp() {
        applicationService = ApplicationService(orderService, paymentAdaptor)
    }

    @Test
    internal fun shouldPayOrderSuccessFulWithCorrectInput() {
        val oid = "123"
        val order = Order("123", "userId", 12.00, null)
        every { orderService.findOrder(oid) } returns order
        val paymentResponseFromZhifubao = PaymentResponseFromZhifubao(PaymentStatus.SUCCESS)
        every { paymentAdaptor.payWithZhifubao(order) } returns paymentResponseFromZhifubao
        every { orderService.pay(order, paymentResponseFromZhifubao) } returns PaymentStatus.SUCCESS

        val paymentResult = applicationService.pay(oid, OrderPaymentConfirmationRequestDto(PaymentType.ZHIFUBAO))

        val expectedResult = PaymentConfirmationResponseDto(PaymentStatus.SUCCESS, null)
        assertEquals(paymentResult.paymentStatus, expectedResult.paymentStatus)
        assertNull(paymentResult.errorMessage)
    }

    @Test
    internal fun shouldThrowExceptionWhenOrderNotFound() {
        val orderId = "not-existing-order-id"
        every { orderService.findOrder(orderId) } throws OrderNotFoundException("Order with id $orderId not found.")

        assertThrows<OrderNotFoundException> {
            applicationService.pay(
                orderId,
                orderPaymentConfirmationRequestDto = OrderPaymentConfirmationRequestDto(PaymentType.ZHIFUBAO)
            )
        }
    }

    @Test
    internal fun shouldThrowExceptionWhenConnectingToZhifubaoError() {
        val oid = "123"
        val order = Order(oid, "userId", 12.00, null)
        every { orderService.findOrder(oid) } returns order
        every { paymentAdaptor.payWithZhifubao(order = order) } throws ZhifubaoConnectionException("Timeout when connecting to Zhifubao")

        assertThrows<ZhifubaoConnectionException> {
            applicationService.pay(
                oid,
                orderPaymentConfirmationRequestDto = OrderPaymentConfirmationRequestDto(PaymentType.ZHIFUBAO)
            )
        }
    }
}

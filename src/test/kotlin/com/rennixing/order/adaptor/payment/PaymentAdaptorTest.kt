package com.rennixing.order.adaptor.payment

import com.rennixing.order.adaptor.apiclient.PaymentFeignClient
import com.rennixing.order.adaptor.apiclient.dto.ZhifubaoPaymentResponseDto
import com.rennixing.order.controller.dto.PaymentStatus
import com.rennixing.order.exception.ZhifubaoConnectionException
import com.rennixing.order.model.Order
import com.rennixing.order.model.TicketType
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class PaymentAdaptorTest {
    @MockK
    private lateinit var paymentFeignClient: PaymentFeignClient
    private lateinit var paymentAdaptor: PaymentAdaptor

    @BeforeEach
    fun setUp() {
        paymentAdaptor = PaymentAdaptor(paymentFeignClient)
    }

    @Test
    internal fun shouldPaySuccessWithFeignClient() {
        val order = Order("123", "abc", 22.00, null, TicketType.AIR, null, null)

        every {
            paymentFeignClient.payWithZhifubao(order.id, any())
        } returns ZhifubaoPaymentResponseDto(PaymentStatus.SUCCESS)

        val paymentResult = paymentAdaptor.payWithZhifubao(order)

        assertEquals(PaymentStatus.SUCCESS, paymentResult.paymentStatus)
    }

    @Test
    internal fun shouldThrowExceptionWhenZhifubaoNotRespondsInOneMinute() {
        val order = Order("123", "abc", 22.00, null, TicketType.AIR, null, null)

        every {
            paymentFeignClient.payWithZhifubao(order.id, any())
        } throws RuntimeException()

        assertThrows<ZhifubaoConnectionException> { paymentAdaptor.payWithZhifubao(order) }
    }
}

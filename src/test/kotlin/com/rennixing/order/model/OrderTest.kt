package com.rennixing.order.model

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class OrderTest{
    @Test
    internal fun shouldFulfillPayment() {
        val toBePaidOrder = Order("123", "abc", 12.00, null)

        toBePaidOrder.applyPayment()

        assertNotNull(toBePaidOrder.paymentFulfillment)
    }
}

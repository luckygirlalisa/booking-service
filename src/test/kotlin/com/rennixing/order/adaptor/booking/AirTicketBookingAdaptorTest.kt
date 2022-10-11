package com.rennixing.order.adaptor.booking

import com.rennixing.order.adaptor.apiclient.AirTicketBookingFeignClient
import com.rennixing.order.controller.dto.TicketCancellationStatus
import com.rennixing.order.exception.AirTicketBookingSystemConenctionException
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
internal class AirTicketBookingAdaptorTest {
    @MockK
    private lateinit var airTicketBookingFeignClient: AirTicketBookingFeignClient
    private lateinit var airTicketBookingAdaptor: AirTicketBookingAdaptor

    @BeforeEach
    fun setUp() {
        airTicketBookingAdaptor = AirTicketBookingAdaptor(airTicketBookingFeignClient)
    }

    @Test
    internal fun shouldCancelTicketSuccessWithFeignClient() {
        val order = Order("123", "abc", 22.00, null, TicketType.AIR, null, null)

        every {
            airTicketBookingFeignClient.cancelTicket(order)
        } returns TicketCancellationStatus.SUCCESS

        val cancellationResult = airTicketBookingAdaptor.cancelTicket(order)

        assertEquals(TicketCancellationStatus.SUCCESS, cancellationResult!!.cancellationStatus)
    }

    @Test
    internal fun shouldThrowExceptionWhenConnectToAirTicketBookingSystemFailed() {
        val order = Order("123", "abc", 22.00, null, TicketType.AIR, null, null)

        every {
            airTicketBookingFeignClient.cancelTicket(order)
        } throws AirTicketBookingSystemConenctionException()

        assertThrows<AirTicketBookingSystemConenctionException> {
            airTicketBookingAdaptor.cancelTicket(order)
        }
    }
}

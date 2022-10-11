package com.rennixing.order.adaptor.booking

import com.rennixing.order.adaptor.apiclient.AirTicketBookingFeignClient
import com.rennixing.order.exception.AirTicketBookingSystemConenctionException
import com.rennixing.order.model.Order
import org.springframework.stereotype.Component

@Component
class AirTicketBookingAdaptor(val airTicketBookingFeignClient: AirTicketBookingFeignClient) {
    fun cancelTicket(order: Order): TicketCancelResponseFrom3rdParty? {
        val ticketCancellationStatus =
            try {
                airTicketBookingFeignClient.cancelTicket(order)
            } catch (e: Exception) {
                throw AirTicketBookingSystemConenctionException("Cancellation failed when connecting with Air Ticket Booking System.")
            }

        return TicketCancelResponseFrom3rdParty(ticketCancellationStatus)
    }
}

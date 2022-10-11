package com.rennixing.order.adaptor.booking

import com.rennixing.order.adaptor.apiclient.AirTicketBookingFeignClient
import com.rennixing.order.model.Order
import org.springframework.stereotype.Component

@Component
class AirTicketBookingAdaptor(val airTicketBookingFeignClient: AirTicketBookingFeignClient) {
    fun cancelTicket(order: Order): TicketCancelResponseFrom3rdParty? {
        return TicketCancelResponseFrom3rdParty(airTicketBookingFeignClient.cancelTicket(order))
    }
}

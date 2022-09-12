package com.rennixing.order.adaptor.booking

import com.rennixing.order.model.Order
import org.springframework.stereotype.Component

@Component
class BookingAdaptor {
    fun cancelTicket(order: Order) : TicketCancelResponseFrom3rdParty? {
        TODO("Not yet implemented")
    }
}

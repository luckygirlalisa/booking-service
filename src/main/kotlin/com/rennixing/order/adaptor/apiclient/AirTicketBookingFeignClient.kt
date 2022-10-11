package com.rennixing.order.adaptor.apiclient

import com.rennixing.order.controller.dto.TicketCancellationStatus
import com.rennixing.order.model.Order
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(
    value = "payment-api",
    url = "\${integration.paymentService.baseUrl}",
)
interface AirTicketBookingFeignClient {
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/payment-orders/{oid}/"]
    )
    fun cancelTicket(
        @RequestBody order: Order
    ): TicketCancellationStatus
}

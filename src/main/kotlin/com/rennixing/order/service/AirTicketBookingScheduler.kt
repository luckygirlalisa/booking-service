package com.rennixing.order.service

import com.rennixing.order.adaptor.apiclient.AirTicketBookingFeignClient
import com.rennixing.order.adaptor.booking.TicketCancelResponseFrom3rdParty
import com.rennixing.order.exception.AirTicketBookingSystemConenctionException
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class AirTicketBookingScheduler(
    val orderService: OrderService,
    val airTicketBookingFeignClient: AirTicketBookingFeignClient
) {
    @Scheduled(cron = "0 0 * * ?")
    @Retryable(value = [AirTicketBookingSystemConenctionException::class], maxAttempts = 4, backoff = Backoff(delay = 1000))
    fun execute() {
        try {
            val allCancelFailedOrders = orderService.findFailedCancellationOrders();
            allCancelFailedOrders.forEach { order ->
                run {
                    val cancelTicketStatus = airTicketBookingFeignClient.cancelTicket(order)
                    orderService.cancelTicket(order, TicketCancelResponseFrom3rdParty(cancelTicketStatus))
                }
            }
        } catch (e: Exception) {
            throw AirTicketBookingSystemConenctionException("Cancellation failed when connecting with Air Ticket Booking System.")
        }
    }
}

package com.rennixing.order.service

import com.rennixing.order.adaptor.payment.PaymentResponseFromZhifubao
import com.rennixing.order.controller.dto.PaymentStatus
import com.rennixing.order.exception.OrderNotFoundException
import com.rennixing.order.model.Order
import com.rennixing.order.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
) {

    fun findOrder(oid: String): Order {
        return orderRepository.findById(oid) ?: throw OrderNotFoundException("Order with id $oid not found")
    }

    fun pay(order: Order, paymentResponseFromZhifubao: PaymentResponseFromZhifubao): PaymentStatus {
        order.applyPayment()
        orderRepository.save(order)
        return paymentResponseFromZhifubao.paymentStatus
    }
}

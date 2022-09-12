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
//    private val paymentFeignClient: PaymentFeignClient,
//    private val messageProducer: MessageProducer
) {

    fun findOrder(oid: String): Order {
        return orderRepository.findById(oid) ?: throw OrderNotFoundException("Order with id $oid not found")
    }

    fun pay(order: Order, paymentResponseFromZhifubao: PaymentResponseFromZhifubao): PaymentStatus {
        order.applyPayment()
        orderRepository.save(order)
        return paymentResponseFromZhifubao.paymentStatus
    }

//    fun createOrder(orderRequestDto: OrderRequestDto): Order {
//        return orderRepository.save(Order(orderRequestDto))
//    }
//
//    fun getOrderSummary(orderId: String): Order? {
//        return orderRepository.findById(orderId)
//    }
//
//    fun applyCancellation(orderId: String, createAt: String): String? {
//        val order = orderRepository.findById(orderId)
//        val fulfilmentExpireAt = order?.applyCancellation(createAt)
//
//        return fulfilmentExpireAt?.also {
//            orderRepository.save(order)
//        }
//    }
//
//    fun confirmCancellation(orderId: String, fulfilledAt: String): String? {
//        val order = orderRepository.findById(orderId)
//        val fulfilmentExpireAt = order?.confirmCancellation(fulfilledAt)
//
//        return fulfilmentExpireAt?.let {
//            val refundResult = paymentFeignClient.refundPayOrder(order.paymentFulfilment!!.payOrderId!!)
//            if (refundResult.refundStatus == "success") {
//                orderRepository.save(order)
//                messageProducer.sendReturnRedPacketMessage(order.paymentFulfilment!!.redPacketId!!)
//                fulfilmentExpireAt
//            } else {
//                null
//            }
//        }
//    }
}

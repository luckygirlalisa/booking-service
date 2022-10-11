package com.rennixing.order.adaptor.payment

import com.rennixing.order.adaptor.apiclient.PaymentFeignClient
import com.rennixing.order.adaptor.apiclient.dto.ZhifubaoPaymentRequestDto
import com.rennixing.order.exception.ZhifubaoConnectionException
import com.rennixing.order.model.Order
import org.springframework.stereotype.Component

@Component
class PaymentAdaptor(val paymentFeignClient: PaymentFeignClient) {
    val MERCHANT_ID = "merchant-id"
    fun payWithZhifubao(order: Order): PaymentResponseFromZhifubao {

        val result = try {
            paymentFeignClient.payWithZhifubao(
                order.id,
                ZhifubaoPaymentRequestDto(
                    MERCHANT_ID,
                    order.id,
                    order.userId,
                    order.price
                )
            )
        } catch (e: RuntimeException) {
            throw ZhifubaoConnectionException("Connection with zhifubao failed.")
        }

        return PaymentResponseFromZhifubao(result!!.paymentStatus)
    }
}

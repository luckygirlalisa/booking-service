package com.rennixing.order.adaptor.apiclient

import com.rennixing.order.adaptor.apiclient.dto.RefundResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(
    value = "payment-api",
    url = "\${integration.paymentService.baseUrl}",
    configuration = [PaymentErrorDecoder::class]
)
interface PaymentFeignClient {

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/payment-orders/{payOrderId}/refund"]
    )
    fun refundPayOrder(@PathVariable payOrderId: String): RefundResponseDto
}

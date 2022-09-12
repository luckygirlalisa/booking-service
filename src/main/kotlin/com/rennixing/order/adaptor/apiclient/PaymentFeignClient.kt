package com.rennixing.order.adaptor.apiclient

import com.rennixing.order.adaptor.apiclient.dto.ZhifubaoPaymentRequestDto
import com.rennixing.order.adaptor.apiclient.dto.ZhifubaoPaymentResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(
    value = "payment-api",
    url = "\${integration.paymentService.baseUrl}",
    configuration = [PaymentErrorDecoder::class]
)
interface PaymentFeignClient {

//    @RequestMapping(
//        method = [RequestMethod.POST],
//        value = ["/payment-orders/{payOrderId}/refund"]
//    )
//    fun refundPayOrder(@PathVariable payOrderId: String): RefundResponseDto

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/payment-orders/{oid}/"]
    )
    fun payWithZhifubao(requestDto: ZhifubaoPaymentRequestDto) : ZhifubaoPaymentResponseDto?
}

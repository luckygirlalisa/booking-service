package com.rennixing.order.adaptor.apiclient

import com.rennixing.order.adaptor.apiclient.dto.ZhifubaoPaymentRequestDto
import com.rennixing.order.adaptor.apiclient.dto.ZhifubaoPaymentResponseDto
import feign.Body
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(
    value = "payment-api",
    url = "\${integration.paymentService.baseUrl}",
)
interface PaymentFeignClient {
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/payment-orders/{oid}/"]
    )
    fun payWithZhifubao(@PathVariable oid: String, @RequestBody requestDto: ZhifubaoPaymentRequestDto) : ZhifubaoPaymentResponseDto?
}

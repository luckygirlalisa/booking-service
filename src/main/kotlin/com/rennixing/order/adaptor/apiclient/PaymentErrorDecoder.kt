package com.rennixing.order.adaptor.apiclient

import com.rennixing.order.exception.PaymentRefundException
import feign.Response
import feign.codec.ErrorDecoder

class PaymentErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception {
        return PaymentRefundException("failed to refund payment")
    }
}

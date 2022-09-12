package com.rennixing.order.adaptor.payment

import com.rennixing.order.adaptor.apiclient.PaymentFeignClient
import com.rennixing.order.adaptor.apiclient.dto.ZhifubaoPaymentRequestDto
import com.rennixing.order.model.Order
import org.springframework.stereotype.Component


@Component
class PaymentAdaptor(val paymentFeignClient: PaymentFeignClient) {
    val MERCHANT_ID = "merchant-id"
    fun payWithZhifubao(order: Order): PaymentResponseFromZhifubao {
        val result = paymentFeignClient.payWithZhifubao(
            ZhifubaoPaymentRequestDto(
                MERCHANT_ID,
                order.id,
                order.userId,
                order.price
            )
        )
        return PaymentResponseFromZhifubao(result!!.paymentStatus)
//        val thread: Thread = Thread(LongRunningTask())
//        thread.start()
//
//        val timer = Timer()
//        val timeOutTask = TimeOutTask(thread, timer)
//        timer.schedule(timeOutTask, 3000)
    }

//    private fun getResponse(): Boolean {
//        val start = System.currentTimeMillis()
//        val end = start + 60 * 1000
//        while (System.currentTimeMillis() < end) {
//            return true
//        }
//        return false
//    }
//
//    private fun connectionWithZhifubao(order: Order): ZhifubaoPaymentResponseDto? {
//        try {
//            return paymentFeignClient.payWithZhifubao(
//                ZhifubaoPaymentRequestDto(
//                    MERCHANT_ID,
//                    order.id,
//                    order.userId,
//                    order.price
//                )
//            )
//        } catch (exception: TimeoutCancellationException) {
//            throw ZhifubaoConnectionException("Connection to Zhifubao failed.")
//        }
//    }
//
//    internal class LongRunningTask : Runnable {
//        override fun run() {
////            for (i in 0 until Long.MAX_VALUE) {
////                if (Thread.interrupted()) {
////                    return
////                }
////            }
//
//        }
//    }
//
//    internal class TimeOutTask(private val thread: Thread?, timer: Timer) : TimerTask() {
//        private val timer: Timer
//
//        init {
//            this.timer = timer
//        }
//
//        override fun run() {
//            if (thread != null && thread.isAlive) {
//                thread.interrupt()
//                timer.cancel()
//            }
//        }
//    }
}

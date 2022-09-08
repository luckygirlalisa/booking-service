package com.rennixing.order.adaptor.kafka

import com.rennixing.order.adaptor.kafka.dto.OrderMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class MessageProducer(
    private val kafkaTemplate: KafkaTemplate<String, OrderMessage>
) {
    @Value("\${integration.kafka.topic}")
    private val topic: String? = null

    fun sendReturnRedPacketMessage(redPacketId: String) =
        kafkaTemplate.send(
            topic!!,
            OrderMessage(
                type = "return_red_packet",
                metadata = "redPacketId=$redPacketId"
            )
        )
}

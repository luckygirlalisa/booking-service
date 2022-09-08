package com.rennixing.order.adaptor.kafka

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
internal class MessageProducerTest {
    @Value("\${integration.kafka.topic}")
    private val topic: String? = null

    @Autowired
    private lateinit var producer: MessageProducer

    @Test
    fun `should send return red packet message successfully given the kafka broker is up and running`() {
        producer.sendReturnRedPacketMessage("redPacketId")
    }
}

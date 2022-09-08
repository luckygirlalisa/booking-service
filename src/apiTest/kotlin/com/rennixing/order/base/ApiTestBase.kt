package com.rennixing.order.base

import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("apitest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
@AutoConfigureWireMock(stubs = ["classpath:/stubs"], port = 17999)
class ApiTestBase {
    @LocalServerPort
    var sprintTestRandomServerPort = 0

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @BeforeEach
    fun setUp() {
        RestAssured.port = sprintTestRandomServerPort
    }
}

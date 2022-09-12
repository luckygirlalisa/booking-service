package com.rennixing.order

import com.fasterxml.jackson.databind.ObjectMapper
import com.rennixing.order.base.ApiTestBase
import com.rennixing.order.controller.dto.OrderPaymentConfirmationRequestDto
import com.rennixing.order.controller.dto.PaymentType
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class OrderPaymentApiTest : ApiTestBase() {
    private val objectMapper = ObjectMapper()

    @BeforeAll
    fun setUpTestData() {
        mongoTemplate.save(orderWaitingForPayment)
    }

    @Test
    fun shouldPayOrderSuccessful() {
        val requestDto = OrderPaymentConfirmationRequestDto(PaymentType.ZHIFUBAO);
        val requestString = objectMapper.writeValueAsString(requestDto)

        RestAssured
            .given()
            .body(requestString)
            .contentType(ContentType.JSON)
            .post("/travel-booking-orders/${orderWaitingForPayment.id}/payment/confirmation")
            .then()
            .statusCode(200)
    }
}

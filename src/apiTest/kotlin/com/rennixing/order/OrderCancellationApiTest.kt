package com.rennixing.order

import com.rennixing.order.base.ApiTestBase
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class OrderCancellationApiTest : ApiTestBase() {
    @BeforeAll
    fun setUpTestData() {
//        mongoTemplate.save(orderStillInPrepPeriod)
//        mongoTemplate.save(orderStillInPrepPeriod2)
//        mongoTemplate.save(orderPendingCancellationConfirmation)
//        mongoTemplate.save(orderPendingCancellationConfirmation2)
//        mongoTemplate.save(orderPendingCancellationConfirmation3)
//        mongoTemplate.save(orderPrepMealFulfilled)
//        mongoTemplate.save(orderPrepMealFulfilled2)
    }

    @Test
    fun `should get 409 error when failed to cancel order`() {
        val requestBody =
            """
                {
                    "createAt": "2021-09-12T00:06:00"
                }
            """.trimIndent()

        RestAssured
            .given()
            .body(requestBody)
            .contentType(ContentType.JSON)
            .post("/orders/orderStillInPrepPeriod/cancellation")
            .then()
            .statusCode(409)
    }

    @Test
    fun `should get 409 error when failed to cancel a multi-item order`() {
        val requestBody =
            """
                {
                    "createAt": "2021-09-12T00:06:00"
                }
            """.trimIndent()

        RestAssured
            .given()
            .body(requestBody)
            .contentType(ContentType.JSON)
            .post("/orders/orderStillInPrepPeriod2/cancellation")
            .then()
            .statusCode(409)
    }

    @Test
    fun `should get 200 when successfully cancelled a multi-item order`() {
        val requestBody =
            """
                {
                    "createAt": "2021-09-12T00:20:00"
                }
            """.trimIndent()

        RestAssured
            .given()
            .body(requestBody)
            .contentType(ContentType.JSON)
            .post("/orders/orderStillInPrepPeriod2/cancellation")
            .then()
            .statusCode(200)
            .body("expireAt", equalTo("2021-09-12T00:25:00"))
    }

    @Test
    fun `should get 200 when successfully confirmed a pending cancellation order`() {
        val requestBody =
            """
                {
                    "fulfilledAt": "2021-09-12T00:23:00"
                }
            """.trimIndent()

        RestAssured
            .given()
            .body(requestBody)
            .contentType(ContentType.JSON)
            .post("/orders/orderPendingCancellationConfirmation/cancellation/confirmation")
            .then()
            .statusCode(200)
            .body("fulfilledAt", equalTo("2021-09-12T00:23:00"))
    }

    @Test
    fun `should get 503 when confirmed a pending cancellation order failed due to payment api error`() {
        val requestBody =
            """
                {
                    "fulfilledAt": "2021-09-12T00:23:00"
                }
            """.trimIndent()

        RestAssured
            .given()
            .body(requestBody)
            .contentType(ContentType.JSON)
            .post("/orders/orderPendingCancellationConfirmation2/cancellation/confirmation")
            .then()
            .statusCode(503)
    }

    @Test
    fun `should get 503 when confirmed a pending cancellation order failed due to payment service not available`() {
        val requestBody =
            """
                {
                    "fulfilledAt": "2021-09-12T00:23:00"
                }
            """.trimIndent()

        RestAssured
            .given()
            .body(requestBody)
            .contentType(ContentType.JSON)
            .post("/orders/orderPendingCancellationConfirmation3/cancellation/confirmation")
            .then()
            .statusCode(503)
    }

    @Test
    fun `should get 409 error when failed to cancel order due to meal already prepared`() {
        val requestBody =
            """
                {
                    "createAt": "2021-09-12T00:02:00"
                }
            """.trimIndent()

        RestAssured
            .given()
            .body(requestBody)
            .contentType(ContentType.JSON)
            .post("/orders/orderPrepMealFulfilled/cancellation")
            .then()
            .statusCode(409)
    }

    @Test
    fun `should get 409 error when failed to cancel order due to meal already prepared even the time is late`() {
        val requestBody =
            """
                {
                    "createAt": "2021-09-12T00:02:00"
                }
            """.trimIndent()

        RestAssured
            .given()
            .body(requestBody)
            .contentType(ContentType.JSON)
            .post("/orders/orderPrepMealFulfilled2/cancellation")
            .then()
            .statusCode(409)
    }
}

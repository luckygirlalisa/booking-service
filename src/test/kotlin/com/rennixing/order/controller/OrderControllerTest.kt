package com.rennixing.order.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.rennixing.order.controller.dto.OrderPaymentConfirmationRequestDto
import com.rennixing.order.controller.dto.PaymentConfirmationResponseDto
import com.rennixing.order.controller.dto.PaymentStatus
import com.rennixing.order.controller.dto.PaymentType
import com.rennixing.order.exception.OrderNotFoundException
import com.rennixing.order.exception.ZhifubaoConnectionException
import com.rennixing.order.service.ApplicationService
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(OrderController::class)
@ExtendWith(SpringExtension::class)
internal class OrderControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var applicationService: ApplicationService
    private val objectMapper = ObjectMapper()

    private lateinit var orderController: OrderController

    @BeforeEach
    fun setUp() {
        orderController = OrderController(applicationService)
    }

    @Test
    fun shouldPayOrderSuccessfulWithCorrectInput() {
        // given
        val requestDto = OrderPaymentConfirmationRequestDto(PaymentType.ZHIFUBAO)
        val requestString = objectMapper.writeValueAsString(requestDto)
        every { applicationService.pay(orderId = "123", any()) } returns
            PaymentConfirmationResponseDto(PaymentStatus.SUCCESS, null)

        // when & then
        mockMvc
            .post("/travel-booking-orders/123/payment/confirmation") {
                contentType = MediaType.APPLICATION_JSON
                content = requestString
            }
            .andExpect {
                status { isOk() }
                jsonPath("$.paymentStatus") { value("SUCCESS") }
                jsonPath("$.errorMessage") { value(null) }
            }
    }

    @Test
    internal fun shouldReturn404WhenOrderNotFoundWithPassedId() {
        val orderPaymentConfirmationRequestDto = OrderPaymentConfirmationRequestDto(PaymentType.ZHIFUBAO)
        val orderId = "not-existing-order-id"
        val errorMessage = "Order with id $orderId not found"
        every { applicationService.pay(orderId, any()) } throws OrderNotFoundException(errorMessage)
        val requestString = objectMapper.writeValueAsString(orderPaymentConfirmationRequestDto)

        mockMvc
            .post("/travel-booking-orders/$orderId/payment/confirmation") {
                contentType = MediaType.APPLICATION_JSON
                content = requestString
            }
            .andExpect {
                status { isNotFound() }
                jsonPath("$.paymentStatus") { value("FAILED") }
                jsonPath("$.errorMessage") { value(errorMessage) }
            }
    }

    @Test
    internal fun shouldReturn400WhenPaymentTypeNotAccepted() {
        val requestString = "{\"paymentType\":\"credit-card\"}"
        val response = mockMvc
            .post("/travel-booking-orders/123/payment/confirmation") {
                contentType = MediaType.APPLICATION_JSON
                content = requestString
            }
        response
            .andExpect {
                status { isBadRequest() }
                jsonPath("$.paymentStatus") { value("FAILED") }
                jsonPath("$.errorMessage") { prefix("Cannot deserialize value of type `com.rennixing.order.controller.dto.PaymentType` from String") }
            }
    }

    @Test
    internal fun shouldReturn500WhenConnectingZhifubaoFailed() {
        // given
        val requestDto = OrderPaymentConfirmationRequestDto(PaymentType.ZHIFUBAO)
        val requestString = objectMapper.writeValueAsString(requestDto)
        val message = "Connecting to Zhifubao failed."
        every { applicationService.pay(orderId = "123", any()) } throws ZhifubaoConnectionException(message)

        mockMvc
            .post("/travel-booking-orders/123/payment/confirmation") {
                contentType = MediaType.APPLICATION_JSON
                content = requestString
            }
            .andExpect {
                status { isInternalServerError() }
                jsonPath("$.paymentStatus") { value("FAILED") }
                jsonPath("$.errorMessage") { value(message) }
            }
    }
}

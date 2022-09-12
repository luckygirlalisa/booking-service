package com.rennixing.order.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.rennixing.order.controller.dto.OrderPaymentConfirmationRequestDto
import com.rennixing.order.controller.dto.PaymentConfirmationResponseDto
import com.rennixing.order.controller.dto.PaymentStatus
import com.rennixing.order.controller.dto.PaymentType
import com.rennixing.order.exception.OrderNotFoundException
import com.rennixing.order.exception.PaymentTypeNotAcceptableException
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
        val requestDto = OrderPaymentConfirmationRequestDto(PaymentType.ZHIFUBAO);
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
        val orderPaymentConfirmationRequestDto = OrderPaymentConfirmationRequestDto(PaymentType.ZHIFUBAO);
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
        val message = "Payment type credit-card not acceptable"
        every { applicationService.pay(orderId = "123", any()) } throws PaymentTypeNotAcceptableException(message)

        mockMvc
            .post("/travel-booking-orders/123/payment/confirmation") {
                contentType = MediaType.APPLICATION_JSON
                content = requestString
            }
            .andExpect {
                status { isBadRequest() }
            }
    }

//
//    @Test
//    fun `should call orderService to create a order and respond whatever returned`() {
//        val newOrderRequest = OrderRequestDto(
//            userId = "userId",
//            merchantId = "merchantId",
//            orderItems = listOf(
//                OrderItem(productId = "product1", price = 15.0, minutesToPrepare = 40),
//                OrderItem(productId = "product2", price = 5.0, minutesToPrepare = 10)
//            )
//        )
//        every { orderService.createOrder(any()) } returns Order(
//            id = "orderId",
//            userId = "userId",
//            merchantId = "merchantId",
//            orderItems = listOf(
//                OrderItem(productId = "product1", price = 15.0, minutesToPrepare = 40),
//                OrderItem(productId = "product2", price = 5.0, minutesToPrepare = 10)
//            )
//        )
//
//        val result = orderController.createOrder(newOrderRequest)
//
//        assertNotNull(result.id)
//        assertEquals("userId", result.userId)
//        assertEquals("merchantId", result.merchantId)
//        assertEquals(2, result.orderItems.size)
//    }
//
//    @Test
//    fun `should respond retrieved order with 200 code`() {
//        val orderId = "orderId"
//        every { orderService.getOrderSummary(orderId) } returns Order(
//            id = "orderId",
//            userId = "userId",
//            merchantId = "merchantId",
//            orderItems = listOf(
//                OrderItem(productId = "product1", price = 15.0, minutesToPrepare = 40),
//                OrderItem(productId = "product2", price = 5.0, minutesToPrepare = 10)
//            )
//        )
//
//        val result = orderController.getOrderSummary(orderId)
//
//        assertEquals(HttpStatus.OK, result.statusCode)
//        assertEquals(orderId, result.body!!.id)
//    }
//
//    @Test
//    fun `should respond retrieved order with 404 code given the order could not be found`() {
//        val orderId = "orderId"
//        every { orderService.getOrderSummary(orderId) } returns null
//
//        val result = orderController.getOrderSummary(orderId)
//
//        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
//    }
//
//    @Test
//    fun `should respond 409 error for cancellation request when order could not be cancelled`() {
//        val orderId = "orderId"
//        val createAt = "2021-09-12T00:06:00"
//        val cancellationRequest = CancellationRequestDto(createAt)
//        every { orderService.applyCancellation(orderId, createAt) } returns null
//
//        val result = orderController.cancellation(orderId, cancellationRequest)
//
//        assertEquals(HttpStatus.CONFLICT, result.statusCode)
//    }
//
//    @Test
//    fun `should respond ok for cancellation request when order could be cancelled`() {
//        val orderId = "orderId"
//        val createAt = "2021-09-12T00:20:00"
//        val cancellationRequest = CancellationRequestDto(createAt)
//        every { orderService.applyCancellation(orderId, createAt) } returns "2021-09-12T00:25:00"
//
//        val result = orderController.cancellation(orderId, cancellationRequest)
//
//        assertEquals(HttpStatus.OK, result.statusCode)
//        assertEquals("2021-09-12T00:25:00", result.body?.expireAt)
//    }
//
//    @Test
//    fun `should respond ok for confirm cancellation when order successfully cancelled`() {
//        val orderId = "orderId"
//        val fulfilledAt = "2021-09-12T00:23:00"
//        val confirmCancellationRequest = ConfirmCancellationRequestDto(fulfilledAt)
//        every { orderService.confirmCancellation(orderId, fulfilledAt) } returns fulfilledAt
//
//        val result = orderController.cancellationConfirmation(orderId, confirmCancellationRequest)
//
//        assertEquals(HttpStatus.OK, result.statusCode)
//        assertEquals("2021-09-12T00:23:00", result.body?.fulfilledAt)
//    }
//
//    @Test
//    fun `should respond 503 for confirm cancellation when order cannot be cancelled because of internal error`() {
//        val orderId = "orderId"
//        val fulfilledAt = "2021-09-12T00:23:00"
//        val confirmCancellationRequest = ConfirmCancellationRequestDto(fulfilledAt)
//        every { orderService.confirmCancellation(orderId, fulfilledAt) } returns null
//
//        val result = orderController.cancellationConfirmation(orderId, confirmCancellationRequest)
//
//        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, result.statusCode)
//    }
}

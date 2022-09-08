package com.rennixing.order.controller.dto

data class CancellationRequestDto(val createAt: String)

data class CancellationResponseDto(val expireAt: String)

data class ConfirmCancellationRequestDto(val fulfilledAt: String)

data class ConfirmCancellationResponseDto(val fulfilledAt: String)

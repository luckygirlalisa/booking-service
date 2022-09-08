package com.rennixing.order.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.toLocalDateTime() = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

fun LocalDateTime.toISOLocalDateTime() = format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

package com.rennixing.order

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients(basePackages = ["com.rennixing.order.adaptor.apiclient"])
class OrderApplication

fun main(args: Array<String>) {
    SpringApplication.run(OrderApplication::class.java, *args)
}

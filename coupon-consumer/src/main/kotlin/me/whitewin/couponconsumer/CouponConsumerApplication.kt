package me.whitewin.couponconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CouponConsumerApplication

fun main(args: Array<String>) {
    runApplication<CouponConsumerApplication>(*args)
}

package me.whitewin.couponapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CouponApiApplication

fun main(args: Array<String>) {
	runApplication<CouponApiApplication>(*args)
}

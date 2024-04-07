package me.whitewin.couponconsumer

import me.whitewin.couponcore.CouponCoreConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableScheduling

@Import(CouponCoreConfiguration::class)
@SpringBootApplication
@EnableScheduling
class CouponConsumerApplication

fun main(args: Array<String>) {
    System.setProperty("spring.config.name", "application-core,application-consumer")
    runApplication<CouponConsumerApplication>(*args)
}

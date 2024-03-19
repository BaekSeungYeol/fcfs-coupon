package me.whitewin.couponapi

import me.whitewin.couponcore.CouponCoreConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@Import(CouponCoreConfiguration::class)
@SpringBootApplication
class CouponApiApplication

fun main(args: Array<String>) {
	System.setProperty("spring.config.name", "application-core,application-consumer")
	runApplication<CouponApiApplication>(*args)
}

package me.whitewin.couponcore

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@ComponentScan
@EnableAutoConfiguration
@EnableCaching
@EnableAspectJAutoProxy(exposeProxy = true)
class CouponCoreConfiguration

package me.whitewin.couponcore.configuration

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfiguration(
//    @Value("\${spring.data.redis.host}") private val host: String,
//    @Value("\${spring.data.redis.port}") private val port: String
) {

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        val redisAddress = "redis://localhost:6379"
        config.useSingleServer().setAddress(redisAddress)
        return Redisson.create(config)
    }

}

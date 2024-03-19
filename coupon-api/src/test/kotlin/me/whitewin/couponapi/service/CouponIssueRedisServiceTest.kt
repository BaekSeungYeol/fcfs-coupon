package me.whitewin.couponapi.service

import me.whitewin.couponcore.repository.redis.RedisRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service


@SpringBootTest
class CouponIssueRedisServiceTest {

    @Autowired
    lateinit var sut: CouponIssueRedisService

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String,String>

    @BeforeEach
    fun clear() {
        val redisKeys = redisTemplate.keys("issue:request:couponId:*")
        redisTemplate.delete(redisKeys)
    }

    @Test
    fun `쿠폰 수량 검증 성공 - 발급 수량이 존재`() {
        val totalIssueQuantity = 10
        val userId = 1L

        val result = sut.availableTotalIssueQuantity(totalIssueQuantity, userId)

        assertThat(result).isTrue()
    }
}
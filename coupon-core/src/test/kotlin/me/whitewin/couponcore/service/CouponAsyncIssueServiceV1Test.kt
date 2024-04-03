package me.whitewin.couponcore.service

import me.whitewin.couponcore.error.ApplicationException
import me.whitewin.couponcore.model.Coupon
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import java.time.LocalTime
import java.time.ZonedDateTime

@SpringBootTest
class CouponAsyncIssueServiceV1Test {


    @Autowired
    lateinit var sut: CouponAsyncIssueServiceV1

    @Autowired
    lateinit var redisTemplate: RedisTemplate<String,String>

    @BeforeEach
    fun clear() {
        val redisKeys = redisTemplate.keys("issue:request:couponId:*")
        val redisKeys2 = redisTemplate.keys("issue:request:*")
        redisTemplate.delete(redisKeys)
        redisTemplate.delete(redisKeys2)
    }


    @Test
    fun `쿠폰 발급 - 쿠폰이 존재하지 않는다면 예외를 반환한다`() {
        // given
        val couponId = 1L
        val userId = 1L

        // when & then
        assertThrows<ApplicationException> { sut.issue(couponId, userId)  }
    }


}
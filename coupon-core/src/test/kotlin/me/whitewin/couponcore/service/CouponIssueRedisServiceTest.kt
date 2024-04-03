package me.whitewin.couponcore.service

import me.whitewin.couponcore.service.CouponIssueRedisService.Companion.getIssueRequestKey
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate


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


    @Test
    fun `쿠폰 중복 발급 검증 - 발급된 내역에 유저가 존재하지 않으면 true 를 반환한다`() {
        // given
        val couponId = 1L
        val userId = 1L

        // when
        val result = sut.availableUserIssueQuantity(couponId, userId)

        // then
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `쿠폰 중복 발급 검증 - 발급된 내역에 유저가 존재하면 false 를 반환한다`() {
        // given
        val couponId = 1L
        val userId = 1L
        redisTemplate.opsForSet().add(getIssueRequestKey(couponId), userId.toString())

        // when
        val result = sut.availableUserIssueQuantity(couponId, userId)

        // then
        assertThat(result).isEqualTo(false)
    }



}
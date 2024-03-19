package me.whitewin.couponcore.service

import me.whitewin.couponcore.model.Coupon
import me.whitewin.couponcore.repository.CouponIssueRepository
import me.whitewin.couponcore.repository.CouponRepository
import me.whitewin.couponcore.error.ResponseCode
import me.whitewin.couponcore.model.CouponIssue
import me.whitewin.couponcore.repository.redis.RedisRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
@Transactional
class CouponAsyncIssueService(
    private val redisRepository: RedisRepository
) {


    fun issue(couponId: Long, userId: Long) {
        val key = "coupon_$couponId"
        val epocheSecond = Instant.now().epochSecond
        redisRepository.zAdd(key, userId.toString(), epocheSecond.toDouble())
    }
}


package me.whitewin.couponcore.service

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import me.whitewin.couponcore.error.ResponseCode
import me.whitewin.couponcore.model.LockExecutor
import me.whitewin.couponcore.repository.redis.RedisRepository
import me.whitewin.couponcore.repository.redis.dto.CouponIssueRequest
import me.whitewin.couponcore.service.CouponIssueRedisService.Companion.getIssueRequestKey
import me.whitewin.couponcore.service.CouponIssueRedisService.Companion.getIssueRequestQueueKey
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponAsyncIssueServiceV2(
    private val redisRepository: RedisRepository,
    private val couponCacheService: CouponCacheService
) {


    fun issue(couponId: Long, userId: Long) {
        val coupon = couponCacheService.getCouponLocalCache(couponId)
        coupon.checkIssuableCoupon()
        issueRequest(couponId, userId, coupon.totalQuantity)
    }

    private fun issueRequest(couponId: Long, userId: Long, totalIssueQuantity: Int) {
        redisRepository.issueRequest(couponId, userId, totalIssueQuantity)
    }
}


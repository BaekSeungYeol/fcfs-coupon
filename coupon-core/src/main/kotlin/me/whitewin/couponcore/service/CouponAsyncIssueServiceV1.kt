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
class CouponAsyncIssueServiceV1(
    private val redisRepository: RedisRepository,
    private val couponIssueRedisService: CouponIssueRedisService,
    private val objectMapper: ObjectMapper,
    private val distributeLockExecutor: LockExecutor,
    private val couponCacheService: CouponCacheService
) {


    fun issue(couponId: Long, userId: Long) {
        val coupon = couponCacheService.getCouponCache(couponId)
        coupon.checkIssuableCoupon()

        distributeLockExecutor.execute("lock_${couponId}", 3000, 3000) {
            couponIssueRedisService.checkCouponIssueQuantity(coupon, userId)
            issueRequest(couponId, userId)
        }
    }

    private fun issueRequest(couponId: Long, userId: Long) {
        val issueRequest = CouponIssueRequest(couponId, userId)

        try {
            val value = objectMapper.writeValueAsString(issueRequest)
            redisRepository.sAdd(getIssueRequestKey(couponId), userId.toString())
            redisRepository.rPush(getIssueRequestQueueKey(), value)
        } catch (e: JsonProcessingException) {
            throw ResponseCode.FAILED_COUPON_ISSUE_REQUEST.build()
        }


    }
}


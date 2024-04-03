package me.whitewin.couponcore.service

import me.whitewin.couponcore.repository.redis.dto.CouponRedisEntity
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CouponCacheService(
    private val couponIssueService: CouponIssueService
) {

    @Cacheable(cacheNames = ["coupon"])
    fun getCouponCache(couponId: Long): CouponRedisEntity {
        val coupon = couponIssueService.findCoupon(couponId)
        return CouponRedisEntity.toCouponEntity(coupon)
    }
}
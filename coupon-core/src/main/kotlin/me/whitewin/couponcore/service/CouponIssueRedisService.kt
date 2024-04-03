package me.whitewin.couponcore.service

import me.whitewin.couponcore.error.ResponseCode
import me.whitewin.couponcore.repository.redis.RedisRepository
import me.whitewin.couponcore.repository.redis.dto.CouponRedisEntity
import org.springframework.stereotype.Service

@Service
class CouponIssueRedisService(
    private val redisRepository: RedisRepository
) {

    fun checkCouponIssueQuantity(couponRedisEntity: CouponRedisEntity, userId: Long) {
        if(!availableTotalIssueQuantity(couponRedisEntity.totalQuantity, couponRedisEntity.id)) {
            throw ResponseCode.INVALID_COUPON_ISSUE_QUANTITY.build()
        }

        if(!availableUserIssueQuantity(couponRedisEntity.id, userId)) {
            throw ResponseCode.COUPON_ISSUE_DUPLICATED.build()
        }

    }

    fun availableTotalIssueQuantity(totalQuantity: Int?, couponId: Long): Boolean {
        val key = getIssueRequestKey(couponId)
        return totalQuantity?.let {
            it > redisRepository.sCard(key)
        } ?: true
    }
    fun availableUserIssueQuantity(couponId: Long, userId: Long): Boolean {
        val key = getIssueRequestKey(couponId)
        return !redisRepository.sIsMember(key, userId.toString())
    }

    companion object {
        fun getIssueRequestKey(couponId: Long): String = "issue:request:couponId:$couponId"
        fun getIssueRequestQueueKey(): String = "issue:request"
    }
}
package me.whitewin.couponapi.service

import me.whitewin.couponcore.repository.redis.RedisRepository
import org.springframework.stereotype.Service

@Service
class CouponIssueRedisService(
    private val redisRepository: RedisRepository
) {

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
    }
}
package me.whitewin.couponcore.repository.redis.dto

data class CouponIssueRequest(
    val couponId: Long,
    val userId: Long
)
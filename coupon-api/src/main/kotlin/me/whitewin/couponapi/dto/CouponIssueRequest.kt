package me.whitewin.couponapi.dto

data class CouponIssueRequest(
    val userId: Long,
    val couponId: Long
) {
}
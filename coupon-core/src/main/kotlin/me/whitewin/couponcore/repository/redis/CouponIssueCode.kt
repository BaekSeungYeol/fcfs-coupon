package me.whitewin.couponcore.repository.redis

import me.whitewin.couponcore.error.ResponseCode

enum class CouponIssueCode(val code: Int) {

    SUCCESS(1),

    DUPLICATED_COUPON_ISSUE(2),

    INVALID_COUPON_ISSUE_QUANTITY(3);

    companion object {
        fun find(code: String): CouponIssueCode {
            val codeValue = code.toInt()
            return entries.find { it.code == codeValue } ?: throw IllegalArgumentException("존재하지 않는 코드입니다.")
        }

        fun checkRequestResult(code: CouponIssueCode) {
            if(code == INVALID_COUPON_ISSUE_QUANTITY) {
                throw ResponseCode.FAILED_COUPON_ISSUE_REQUEST.build()
            }

            if(code == DUPLICATED_COUPON_ISSUE) {
                throw ResponseCode.FAILED_COUPON_ISSUE_REQUEST.build()
            }

        }
    }
}
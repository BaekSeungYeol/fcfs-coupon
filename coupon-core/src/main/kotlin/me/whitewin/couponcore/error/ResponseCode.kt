package me.whitewin.couponcore.error

import org.springframework.http.HttpStatus


enum class ResponseCode(
    private val httpStatus: HttpStatus,
    private val code: String,
    private val reason: String?) {

    NOT_ENOUGH_COUPON_QUANTITY(HttpStatus.BAD_REQUEST, "NEC-4000", "충분한 수량이 존재하지 않습니다."),
    INVALID_COUPON_DATE(HttpStatus.BAD_REQUEST, "NEC-4001", "유효한 기간이 아닙니다."),

    COUPON_NOT_EXISTS(HttpStatus.BAD_REQUEST, "NEC-4002", "쿠폰이 존재하지 않습니다."),
    COUPON_ISSUE_DUPLICATED(HttpStatus.BAD_REQUEST, "NEC-4003", "이미 발급된 쿠폰입니다."),
    INVALID_COUPON_ISSUE_QUANTITY(HttpStatus.BAD_REQUEST, "NEC-4004", "발급 가능한 수량을 초과합니다."),
    FAILED_COUPON_ISSUE_REQUEST(HttpStatus.BAD_REQUEST, "NEC-4005", "쿠폰 발급이 실패했습니다.");

    fun build(): ApplicationException {
        return ApplicationException(httpStatus, code, reason)
    }
}

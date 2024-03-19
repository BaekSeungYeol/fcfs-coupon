package me.whitewin.couponcore.error

import org.springframework.http.HttpStatus


data class ApplicationException(
    val httpStatus: HttpStatus,
    val code: String,
    val reason: String?
): RuntimeException(
) {
}
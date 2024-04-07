package me.whitewin.couponcore.repository.redis.dto

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import me.whitewin.couponcore.error.ResponseCode
import me.whitewin.couponcore.model.Coupon
import me.whitewin.couponcore.model.CouponType
import java.time.ZonedDateTime

data class CouponRedisEntity(
    val id: Long,
    val couponType: CouponType,
    val totalQuantity: Int,
    val available: Boolean,
    @JsonSerialize(using = ZonedDateTimeSerializer::class) val dateIssueStart: ZonedDateTime?,
    @JsonSerialize(using = ZonedDateTimeSerializer::class) val dateIssueEnd: ZonedDateTime?
) {

    companion object {
        fun toCouponEntity(coupon: Coupon): CouponRedisEntity {
            return CouponRedisEntity(
                coupon.id,
                coupon.couponType,
                coupon.totalQuantity ?: 0,
                coupon.hasAvailableQuantity(),
                coupon.dateIssueStart,
                coupon.dateIssueEnd
            )
        }

    }

    private fun availableIssueDate(): Boolean  {
        val now = ZonedDateTime.now()
        return now.isBefore(dateIssueEnd) && now.isAfter(dateIssueStart)
    }

    fun checkIssuableCoupon() {
        if(!available) {
            throw ResponseCode.ALL_COUPON_DONE.build()
        }
        if(!availableIssueDate()) {
            throw ResponseCode.FAILED_COUPON_ISSUE_REQUEST.build()
        }
    }
}
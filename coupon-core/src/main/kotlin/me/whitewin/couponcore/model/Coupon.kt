package me.whitewin.couponcore.model

import jakarta.persistence.*
import me.whitewin.couponcore.error.ResponseCode
import java.time.ZonedDateTime
import org.hibernate.validator.constraints.Length

@Entity
@Table(name = "coupons")
class Coupon(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    @field:Length(min = 1, max = 15)
    var title: String = "",

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    var couponType: CouponType = CouponType.FIRST_COME_FIRST_SERVED,

    @Column(nullable = true)
    var totalQuantity: Int? = null,

    @Column(nullable = false)
    var issuedQuantity: Int = 0, // 발급 수량 관리

    @Column(nullable = false)
    var discountAmount: Int = 0,

    @Column(nullable = false)
    var minAvailableAmount: Int = 0,

    @Column(nullable = false)
    var dateIssueStart: ZonedDateTime? = null,

    @Column(nullable = false)
    var dateIssueEnd: ZonedDateTime? = null

): BaseTimeEntity() {

    fun hasAvailableQuantity(): Boolean {
        return totalQuantity?.let {
            it > issuedQuantity
        } ?: true
    }

    fun isValidDate(): Boolean {
        val now = ZonedDateTime.now()
        return dateIssueStart?.isBefore(now) ?: false && dateIssueEnd?.isAfter(now) ?: false
    }

    fun issue() {

        if(!hasAvailableQuantity()) {
            throw ResponseCode.NOT_ENOUGH_COUPON_QUANTITY.build()
        }

        if(!isValidDate()) {
            throw ResponseCode.INVALID_COUPON_DATE.build()
        }

        issuedQuantity++
    }
}
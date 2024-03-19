package me.whitewin.couponcore.model

import jakarta.persistence.*
import java.time.ZonedDateTime
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.CreatedDate


@Entity
@Table(name = "coupon_issues")
class CouponIssue(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var couponId: Long = 0,

    @Column(nullable = false)
    var userId: Long = 0, // 발급 수량 관리

    @Column(nullable = false)
    @CreatedDate
    var dateIssued: ZonedDateTime = ZonedDateTime.now(),

    @Column(nullable = true)
    var dateused: ZonedDateTime = ZonedDateTime.now()
): BaseTimeEntity() {

}
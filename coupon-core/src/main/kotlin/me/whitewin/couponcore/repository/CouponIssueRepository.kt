package me.whitewin.couponcore.repository

import me.whitewin.couponcore.model.CouponIssue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CouponIssueRepository: JpaRepository<CouponIssue, Long> {
    fun findFirstByCouponIdEqualsAndUserId(couponId: Long, userId: Long): CouponIssue?
}
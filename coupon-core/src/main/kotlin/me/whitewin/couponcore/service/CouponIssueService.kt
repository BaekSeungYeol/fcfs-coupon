package me.whitewin.couponcore.service

import me.whitewin.couponcore.model.Coupon
import me.whitewin.couponcore.repository.CouponIssueRepository
import me.whitewin.couponcore.repository.CouponRepository
import me.whitewin.couponcore.error.ResponseCode
import me.whitewin.couponcore.model.CouponIssue
import me.whitewin.couponcore.model.event.CouponIssueCompleteEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CouponIssueService(
    private val couponIssueRepository: CouponIssueRepository,
    private val couponRepository: CouponRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {


    fun issue(couponId: Long, userId: Long) {
        val coupon = findCoupon(couponId)
        coupon.issue()
        saveCouponIssue(couponId, userId)
        publishCouponEvent(coupon)
    }

    @Transactional(readOnly = true)
    fun findCoupon(couponId: Long): Coupon {
        return couponRepository.findByIdOrNull(couponId) ?: throw ResponseCode.COUPON_NOT_EXISTS.build()
    }

    private fun saveCouponIssue(couponId: Long, userId: Long): CouponIssue {
        isAlreadyIssued(couponId, userId)

        val couponIssue = CouponIssue(
            couponId = couponId,
            userId = userId
        )

        return couponIssueRepository.save(couponIssue)
    }

    private fun isAlreadyIssued(couponId: Long, userId: Long) {
        couponIssueRepository.findFirstByCouponIdEqualsAndUserId(couponId, userId) ?: throw ResponseCode.COUPON_ISSUE_DUPLICATED.build()
    }

    private fun publishCouponEvent(coupon: Coupon) {
        if(coupon.isIssueComplete()) {
            applicationEventPublisher.publishEvent(CouponIssueCompleteEvent(coupon.id))
        }
    }
}


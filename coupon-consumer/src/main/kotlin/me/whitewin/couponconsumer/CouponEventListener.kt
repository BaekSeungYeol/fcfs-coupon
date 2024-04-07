package me.whitewin.couponconsumer

import me.whitewin.couponcore.model.event.CouponIssueCompleteEvent
import me.whitewin.couponcore.service.CouponCacheService
import mu.KLogging
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CouponEventListener(
    private val couponCacheService: CouponCacheService
) {

    @TransactionalEventListener
    fun issueComplete(event: CouponIssueCompleteEvent) {
        logger.info("issue complete. cache refresh start")
        couponCacheService.getCouponCache(event.couponId)
        couponCacheService.getCouponLocalCache(event.couponId)

    }

    companion object: KLogging()
}
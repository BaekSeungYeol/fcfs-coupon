package me.whitewin.couponapi.service

import me.whitewin.couponapi.dto.CouponIssueRequest
import me.whitewin.couponcore.model.LockExecutor
import me.whitewin.couponcore.service.CouponAsyncIssueServiceV1
import me.whitewin.couponcore.service.CouponIssueService
import org.springframework.stereotype.Service

@Service
class CouponIssueApiService(
    private val couponIssueService: CouponIssueService,
    private val lockExecutor: LockExecutor,
    private val couponAsyncIssueService: CouponAsyncIssueServiceV1
) {

    fun issueRequest(couponIssueRequest: CouponIssueRequest) {
        lockExecutor.execute("lock_${couponIssueRequest.couponId}", 10000, 10000) {
            couponIssueService.issue(couponIssueRequest.couponId, couponIssueRequest.userId)
        }
    }

    fun asyncIssueRequest(couponIssueRequest: CouponIssueRequest) {
        lockExecutor.execute("lock_${couponIssueRequest.couponId}", 10000, 10000) {
            couponAsyncIssueService.issue(couponIssueRequest.couponId, couponIssueRequest.userId)
        }
    }
}
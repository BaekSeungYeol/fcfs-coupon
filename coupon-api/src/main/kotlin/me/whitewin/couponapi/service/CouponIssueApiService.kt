package me.whitewin.couponapi.service

import me.whitewin.couponapi.dto.CouponIssueRequest
import me.whitewin.couponcore.model.LockExecutor
import me.whitewin.couponcore.service.CouponAsyncIssueServiceV1
import me.whitewin.couponcore.service.CouponAsyncIssueServiceV2
import me.whitewin.couponcore.service.CouponIssueService
import org.springframework.stereotype.Service

@Service
class CouponIssueApiService(
    private val couponIssueService: CouponIssueService,
    private val lockExecutor: LockExecutor,
    private val couponAsyncIssueServiceV1: CouponAsyncIssueServiceV1,
    private val couponAsyncIssueServiceV2: CouponAsyncIssueServiceV2
) {

    fun issueRequest(couponIssueRequest: CouponIssueRequest) {
        lockExecutor.execute("lock_${couponIssueRequest.couponId}", 10000, 10000) {
            couponIssueService.issue(couponIssueRequest.couponId, couponIssueRequest.userId)
        }
    }

    fun asyncIssueRequestV1(couponIssueRequest: CouponIssueRequest) {
        return couponAsyncIssueServiceV1.issue(couponIssueRequest.couponId, couponIssueRequest.userId)
    }

    fun asyncIssueRequestV2(couponIssueRequest: CouponIssueRequest) {
        return couponAsyncIssueServiceV2.issue(couponIssueRequest.couponId, couponIssueRequest.userId)
    }


}
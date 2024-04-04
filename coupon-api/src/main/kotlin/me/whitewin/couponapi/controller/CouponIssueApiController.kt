package me.whitewin.couponapi.controller

import me.whitewin.couponapi.dto.CouponIssueRequest
import me.whitewin.couponapi.service.CouponIssueApiService
import me.whitewin.couponcore.service.CouponAsyncIssueServiceV1
import me.whitewin.couponcore.service.CouponAsyncIssueServiceV2
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api")
class CouponIssueApiController(
    private val couponIssueApiService: CouponIssueApiService,
) {

    @PostMapping
    fun issueV1(@RequestBody couponIssueRequest: CouponIssueRequest): Boolean {
        couponIssueApiService.issueRequest(couponIssueRequest)
        return true
    }


    @PostMapping("/v1/issue/async")
    fun issueV1Async(@RequestBody couponIssueRequest: CouponIssueRequest): Boolean {
        couponIssueApiService.asyncIssueRequestV1(couponIssueRequest)
        return true
    }

    @PostMapping("/v2/issue/async")
    fun issueV2Async(@RequestBody couponIssueRequest: CouponIssueRequest): Boolean {
        couponIssueApiService.asyncIssueRequestV2(couponIssueRequest)
        return true
    }

}
package me.whitewin.couponapi.controller

import me.whitewin.couponapi.dto.CouponIssueRequest
import me.whitewin.couponapi.service.CouponIssueApiService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/v1/issue")
class CouponIssueApiController(
    private val couponIssueApiService: CouponIssueApiService
) {

    @PostMapping
    fun issueV1(@RequestBody couponIssueRequest: CouponIssueRequest): Boolean {
        couponIssueApiService.issueRequest(couponIssueRequest)
        return true
    }


    @PostMapping("/async")
    fun issueV1Async(@RequestBody couponIssueRequest: CouponIssueRequest): Boolean {
        couponIssueApiService.issueRequest(couponIssueRequest)
        return true
    }
}
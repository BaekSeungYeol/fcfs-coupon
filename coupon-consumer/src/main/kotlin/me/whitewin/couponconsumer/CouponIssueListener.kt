package me.whitewin.couponconsumer

import com.fasterxml.jackson.databind.ObjectMapper
import me.whitewin.couponcore.repository.redis.RedisRepository
import me.whitewin.couponcore.repository.redis.dto.CouponIssueRequest
import me.whitewin.couponcore.service.CouponIssueRedisService.Companion.getIssueRequestQueueKey
import me.whitewin.couponcore.service.CouponIssueService
import mu.KLogging
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlin.math.log

@Component
class CouponIssueListener(
    private val redisRepository: RedisRepository,
    private val couponIssueService: CouponIssueService,
    private val objectMapper: ObjectMapper
) {

    private val issueRequestQueueKey = getIssueRequestQueueKey()

    @Scheduled(fixedDelay = 1000L)
    fun issue() {
        while(existCouponIssueTarget()) {
            val target = getIssueTarget()
            logger.info("발급 시작")
            couponIssueService.issue(target.couponId, target.userId)
            logger.info("발급 완료")
            removeIssuedTarget()
        }
    }

    private fun existCouponIssueTarget(): Boolean {
        val size =  redisRepository.lSize(issueRequestQueueKey) ?: return false
        return size > 0
    }


    private fun getIssueTarget(): CouponIssueRequest {
        val value = redisRepository.lIndex(issueRequestQueueKey, 0) ?: throw IllegalArgumentException()
        return objectMapper.readValue(value, CouponIssueRequest::class.java)
    }

    private fun removeIssuedTarget() {
        redisRepository.lPop(issueRequestQueueKey)
    }

    companion object: KLogging()
}
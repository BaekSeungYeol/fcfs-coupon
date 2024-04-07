package me.whitewin.couponcore.repository.redis

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import me.whitewin.couponcore.error.ResponseCode
import me.whitewin.couponcore.repository.redis.dto.CouponIssueRequest
import me.whitewin.couponcore.service.CouponIssueRedisService.Companion.getIssueRequestKey
import me.whitewin.couponcore.service.CouponIssueRedisService.Companion.getIssueRequestQueueKey
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.stereotype.Repository

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {

    private val issueScript = issueRequestScript()

    fun zAdd(key: String, value: String, score: Double): Boolean {
        // ZADD NX -> log n
        // 데이터가 없는 경우만 요청 처리
        // score 가 같다면? VALue 값으로 정렬이 될텐데
        // 정렬이 계속 바뀌는데서 오는 문제
        // score 을 관리할 필요가 있을까?
        return redisTemplate.opsForZSet().addIfAbsent(key,value,score) ?: false
    }

    fun sAdd(key: String, value: String): Long {
        return redisTemplate.opsForSet().add(key,value) ?: throw IllegalStateException()
    }

    fun rPush(key: String, value: String): Long {
        return redisTemplate.opsForList().rightPush(key, value) ?: throw IllegalStateException()
    }

    fun sCard(key: String): Long {
        return redisTemplate.opsForSet().size(key) ?: throw IllegalStateException()
    }

    fun sIsMember(key: String, value: String): Boolean {
        return redisTemplate.opsForSet().isMember(key,value) ?: false
    }

    fun lSize(key: String): Long? {
        return redisTemplate.opsForList().size(key)
    }

    fun lIndex(key: String, index: Long): String? {
     return redisTemplate.opsForList().index(key, index)
    }

    fun lPop(key: String): String? {
        return redisTemplate.opsForList().leftPop(key)
    }

    fun issueRequest(couponId: Long, userId: Long, totalIssueQuantity: Int) {
        val issueRequestKey = getIssueRequestKey(couponId)
        val couponIssueRequest = CouponIssueRequest(couponId, userId)
        try {
            val code = redisTemplate.execute(
                issueScript,
                listOf(issueRequestKey, getIssueRequestQueueKey()),
                userId.toString(),
                totalIssueQuantity.toString(),
                objectMapper.writeValueAsString(couponIssueRequest)
            )

            CouponIssueCode.checkRequestResult(CouponIssueCode.find(code))

        } catch (e: JsonProcessingException) {
            throw ResponseCode.FAILED_COUPON_ISSUE_REQUEST.build()
        }
    }

    private final fun issueRequestScript(): RedisScript<String> {
        val script =  """
            if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 1 then
                return '2'
            end
            
            if tonumber(ARGV[2]) > redis.call('SCARD', KEYS[1]) then
                redis.call('SADD', KEYS[1], ARGV[1])
                redis.call('RPUSH', KEYS[2], ARGV[3])
                return '1'
            end
            
            return '3'                
        """.trimIndent()

        return RedisScript.of(script, String::class.java)
    }




}
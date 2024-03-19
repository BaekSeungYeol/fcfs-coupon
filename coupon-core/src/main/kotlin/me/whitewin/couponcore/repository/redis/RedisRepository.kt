package me.whitewin.couponcore.repository.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun zAdd(key: String, value: String, score: Double): Boolean {
        // ZADD NX -> log n
        // 데이터가 없는 경우만 요청 처리
        // score 가 같다면? VALue 값으로 정렬이 될텐데
        // 정렬이 계속 바뀌는데서 오는 문제
        // score 을 관리할 필요가 있을까?
        return redisTemplate.opsForZSet().addIfAbsent(key,value,score) ?: false
    }

    fun sAdd(key: String, value: String, score: Double): Long {
        return redisTemplate.opsForSet().add(key,value) ?: throw IllegalStateException()
    }

    fun sCard(key: String): Long {
        return redisTemplate.opsForSet().size(key) ?: throw IllegalStateException()
    }

    fun sIsMember(key: String, value: String): Boolean {
        return redisTemplate.opsForSet().isMember(key,value) ?: false
    }

}
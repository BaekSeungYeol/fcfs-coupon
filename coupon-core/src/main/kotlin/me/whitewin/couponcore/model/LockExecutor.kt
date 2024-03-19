package me.whitewin.couponcore.model

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class LockExecutor(
    private val redissonClient: RedissonClient
) {

    fun execute(name: String, waitMilliSecond: Long, leaseMilliSecond: Long, logic: Runnable) {
        val lock = redissonClient.getLock(name)
        try {
            val isLocked = lock.tryLock(waitMilliSecond, leaseMilliSecond, TimeUnit.MILLISECONDS)
            if(!isLocked) {
                throw IllegalStateException("$name Lock 획득 실패")
            }
            logic.run()
        } finally {
            lock.unlock()
        }
    }
}

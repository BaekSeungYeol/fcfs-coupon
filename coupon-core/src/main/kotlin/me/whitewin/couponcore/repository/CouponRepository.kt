package me.whitewin.couponcore.repository

import jakarta.persistence.LockModeType
import me.whitewin.couponcore.model.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CouponRepository: JpaRepository<Coupon, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.id = :id")
    fun findByIdWithLock(id: Long): Coupon?
}
package me.whitewin.couponcore.model

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @Column(name = "created_at")
    @CreatedDate
    var createdAt: ZonedDateTime = ZonedDateTime.now()

    @Column(name = "modified_at")
    @LastModifiedDate
    var modifiedAt: ZonedDateTime = ZonedDateTime.now()


}
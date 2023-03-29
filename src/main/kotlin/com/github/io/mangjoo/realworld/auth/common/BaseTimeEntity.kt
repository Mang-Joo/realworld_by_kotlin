package com.github.io.mangjoo.realworld.auth.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseTimeEntity {
    @Column(name = "created_date")
    @CreatedDate
    val createdDate: LocalDateTime? = null

    @Column(name = "modified_date")
    @LastModifiedDate
    val modifiedDate: LocalDateTime? = null
}
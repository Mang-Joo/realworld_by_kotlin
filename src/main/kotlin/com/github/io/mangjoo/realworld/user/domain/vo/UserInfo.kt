package com.github.io.mangjoo.realworld.user.domain.vo

import com.github.io.mangjoo.realworld.user.domain.vo.Role.*
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType.*
import jakarta.persistence.Enumerated
import jakarta.persistence.Transient

@Embeddable
data class UserInfo(
    @Column(unique = true)
    var email: String,
    @Column(unique = true)
    var username: String,
    var bio: String = "",
    var image: String = "",
    @Enumerated(STRING)
    @Column(name = "role", columnDefinition = "ENUM('ROLE_USER','ROLE_ADMIN')")
    var role: Role = ROLE_USER,
    @Transient
    var token: String? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserInfo

        return email == other.email
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }
}
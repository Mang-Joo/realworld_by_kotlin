package com.github.io.mangjoo.realworld.user.domain

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import jakarta.persistence.Embeddable

@Embeddable
class Follow(
    var fromUser: Long,
    var toUser: Long
) : BaseTimeEntity() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Follow

        if (fromUser != other.fromUser) return false
        return toUser == other.toUser
    }

    override fun hashCode(): Int {
        var result = fromUser.hashCode()
        result = 31 * result + toUser.hashCode()
        return result
    }
}
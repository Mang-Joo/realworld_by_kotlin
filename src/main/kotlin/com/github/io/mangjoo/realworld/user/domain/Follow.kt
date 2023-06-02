package com.github.io.mangjoo.realworld.user.domain

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_follow")
class Follow(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    val id: Long = 0,
    @Column(name = "from_user")
    var fromUser: Long,
    @Column(name = "to_user")
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
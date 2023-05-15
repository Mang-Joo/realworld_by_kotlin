package com.github.io.mangjoo.realworld.user.repository

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import com.github.io.mangjoo.realworld.user.domain.Role
import com.github.io.mangjoo.realworld.user.domain.Role.*
import jakarta.persistence.*
import jakarta.persistence.EnumType.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "user_table")
@SQLDelete(sql = "UPDATE userTable SET is_enabled = false WHERE id = ?")
@Where(clause = "is_enabled=true")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @Column(unique = true)
    var email: String,
    var password: String,
    var username: String,
    var bio: String,
    var image: String,
    var isEnabled: Boolean,
    @Enumerated(STRING)
    var role: Role
) : BaseTimeEntity() {
    constructor(
        email: String,
        password: String,
        username: String,
        bio: String,
        image: String,
        isEnabled: Boolean,
        role: Role
    ) : this(
        id = 0,
        email = email,
        password = password,
        username = username,
        bio = bio,
        image = image,
        isEnabled = isEnabled,
        role = role
    )

    constructor(
        email: String,
        password: String,
        username: String
    ) : this(
        id = 0,
        email = email,
        password = password,
        username = username,
        bio = "",
        image = "",
        isEnabled = true,
        role = ROLE_USER
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserEntity

        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        return email.hashCode()
    }


}
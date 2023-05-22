package com.github.io.mangjoo.realworld.user.domain

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import com.github.io.mangjoo.realworld.user.domain.Role.ROLE_USER
import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "user_table")
@SQLDelete(sql = "UPDATE userTable SET is_enabled = false WHERE id = ?")
@Where(clause = "is_enabled=true")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long,
    @Embedded
    var userInfo: UserInfo,
    var password: String,
    var isEnabled: Boolean,
) : BaseTimeEntity() {
    val email get(): String = userInfo.email

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
        userInfo = UserInfo(
            email = email,
            username = username,
            bio = bio,
            image = image,
            role = role
        ),
        password = password,
        isEnabled = isEnabled,
    )

    constructor(
        email: String,
        password: String,
        username: String
    ) : this(
        id = 0,
        userInfo = UserInfo(
            email = email,
            username = username,
            bio = "",
            image = "",
            role = ROLE_USER
        ),
        password = password,
        isEnabled = true,
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}
package com.github.io.mangjoo.realworld.user.domain

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import com.github.io.mangjoo.realworld.user.domain.vo.Role
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
    var id: Long = 0,

    @Embedded
    var userInfo: UserInfo,

    var password: String,
    var isEnabled: Boolean = true,

    @OneToMany(mappedBy = "follower", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var followers: MutableSet<Follow> = mutableSetOf(),

    @OneToMany(mappedBy = "following", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    var followings: MutableSet<Follow> = mutableSetOf(),

) : BaseTimeEntity() {
    val email get(): String = userInfo.email
    val role get(): Role = userInfo.role
    val username get(): String = userInfo.username
    val bio get(): String = userInfo.bio
    val image get(): String = userInfo.image

    fun isFollow(following: User): Boolean = followers.any { it.follower == following }

    fun updateUserInfo(userInfo: UserInfo) = apply { this.userInfo = userInfo }

    fun updatePassword(encodePassword: String) = apply { this.password = encodePassword }

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
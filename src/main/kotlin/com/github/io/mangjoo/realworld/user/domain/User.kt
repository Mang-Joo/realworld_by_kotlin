package com.github.io.mangjoo.realworld.user.domain

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import com.github.io.mangjoo.realworld.user.domain.vo.Role
import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where

@Entity
@Table(name = "user_table")
@SQLDelete(sql = "UPDATE user_table SET is_enabled = false WHERE id = ?")
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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user")
    val followers: MutableSet<Follow> = mutableSetOf(),

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    val followings: MutableSet<Follow> = mutableSetOf(),
) : BaseTimeEntity() {
    val email get(): String = userInfo.email
    val role get(): Role = userInfo.role
    val username get(): String = userInfo.username
    val bio get(): String = userInfo.bio
    val image get(): String = userInfo.image

    fun follow(to: User) = if (this.isFollowing(to.id)) {
        this
    } else {
        apply {
            val follow = Follow(fromUser = this.id, toUser = to.id)
            followings.add(follow)
            addFollower(to, follow)
        }
    }

    private fun addFollower(to: User, follow: Follow) = apply {
        to.followers.add(follow)
    }

    fun unFollow(to: User) = if (to.isFollowing(id)) {
        apply {
            val follow = Follow(fromUser = this.id, toUser = to.id)
            followings.remove(follow)
            removeFollower(follow, to)
        }
    } else {
        this
    }

    private fun removeFollower(following: Follow, to: User) = apply {
        to.followers.remove(following)
    }

    fun followerCount(): Int = followers.size
    fun followingCount(): Int = followings.size

    fun isFollowing(to: Long): Boolean = followings.any { it.toUser == to }

    fun isFollower(from: Long): Boolean = followers.any { it.fromUser == from }

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
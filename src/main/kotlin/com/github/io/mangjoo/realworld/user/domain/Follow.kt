package com.github.io.mangjoo.realworld.user.domain

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "follow")
class Follow(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    var id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "follower_id")
    var from: User,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "following_id")
    var to: User
) : BaseTimeEntity() {

}
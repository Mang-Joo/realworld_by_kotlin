package com.github.io.mangjoo.realworld.article.domain

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import com.github.io.mangjoo.realworld.user.domain.User
import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "comment_table")
class Comment(
    @Id @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val body: String,
    @OneToOne @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "author_id")
    val author: User,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    val article: Article
) : BaseTimeEntity(){
    val authorId get() = author.id
    val slug get() = article.slug
    fun isNormal(userId: Long, slug: String): Boolean = authorId == userId
}
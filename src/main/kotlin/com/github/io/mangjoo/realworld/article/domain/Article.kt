package com.github.io.mangjoo.realworld.article.domain

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import com.github.io.mangjoo.realworld.user.domain.User
import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "article")
class Article(
    @Id
    @Column(name = "article_id")
    val id: Long = 0,
    val title: String,
    val description: String,
    val body: String,
    @Embedded
    val tags: Tags = Tags(),
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "author_id")
    val user: User
) : BaseTimeEntity() {

}
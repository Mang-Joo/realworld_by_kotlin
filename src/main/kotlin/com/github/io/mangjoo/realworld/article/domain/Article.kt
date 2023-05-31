package com.github.io.mangjoo.realworld.article.domain

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import com.github.io.mangjoo.realworld.user.domain.User
import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "article_table")
class Article(
    @Id
    @Column(name = "article_id")
    val id: Long = 0,
    val title: String,
    val description: String,
    val body: String,
    @Embedded
    val tags: Tags = Tags(),
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "author_id")
    val author: User,
    @ElementCollection(fetch = FetchType.EAGER)
    val favorited: MutableSet<String> = mutableSetOf(),
) : BaseTimeEntity() {
    constructor(
        title: String,
        description: String,
        body: String,
        tags: MutableSet<String>,
        author: User
    ) : this(
        title = title,
        description = description,
        body = body,
        tags = Tags(tags),
        author = author,
    )

    fun tags(): MutableSet<String> = tags.group

    fun hasFavorite(): Boolean = favorited.isNotEmpty()

    fun isFavorite(username: String): Boolean = favorited.contains(username)

    fun favoriteCount(): Int = favorited.size

    override fun toString(): String {
        return "Article(id=$id, title='$title', description='$description', body='$body', tags=$tags, author='$author', favorited=$favorited)"
    }


}
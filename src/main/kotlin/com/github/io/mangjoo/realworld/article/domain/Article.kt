package com.github.io.mangjoo.realworld.article.domain

import com.github.io.mangjoo.realworld.auth.common.BaseTimeEntity
import com.github.io.mangjoo.realworld.user.domain.User
import jakarta.persistence.*
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import org.hibernate.annotations.SQLDelete

@Entity
@Table(name = "article_table")
class Article(
    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val slug: String,
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
    @CollectionTable(name = "article_favorited", joinColumns = [JoinColumn(name = "article_id")])
    val favorited: MutableSet<String> = mutableSetOf(),
) : BaseTimeEntity() {
    constructor(
        title: String,
        description: String,
        body: String,
        tags: MutableSet<String>,
        author: User
    ) : this(
        slug = title.replace(" ", "-"),
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
    fun addFavorite(username: String) = favorited.add(username)

    override fun toString(): String {
        return "Article(id=$id, title='$title', description='$description', body='$body', tags=$tags, author='$author', favorited=$favorited)"
    }

    fun update(title: String, body: String, description: String) = Article(
        id = this.id,
        slug = title.replace(" ", "-"),
        title = title,
        description = description,
        body = body,
        tags = this.tags,
        author = this.author,
        favorited = this.favorited
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Article

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}
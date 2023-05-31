package com.github.io.mangjoo.realworld.article.domain

import com.github.io.mangjoo.realworld.fixture.Fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArticleTest {

    @Test
    fun getTagList() {
        val tags = mutableSetOf("test1", "test2")
        val article = Article(
            title = "test",
            description = "test",
            body = "test",
            tags = tags,
            Fixture().user
        )

        assertThat(article.tags()).isEqualTo(tags)
    }

    @Test
    fun hasFavorite() {
        val article = Article(
            title = "test",
            description = "test",
            body = "test",
            tags = mutableSetOf("test1", "test2"),
            author = Fixture().user
        )

        assertThat(article.hasFavorite()).isFalse()
    }

    @Test
    fun addFavorite() {
        val article = Article(
            title = "test",
            description = "test",
            body = "test",
            tags = mutableSetOf("test1", "test2"),
            author = Fixture().user
        )

        article.addFavorite("username")
        assertThat(article.favoriteCount()).isEqualTo(1)
    }

    @Test
    fun isFavorite() {
        val article = Article(
            title = "test",
            description = "test",
            body = "test",
            tags = mutableSetOf("test1", "test2"),
            author = Fixture().user
        )

        val username = "username"
        article.addFavorite(username)

        assertThat(article.isFavorite("test")).isFalse()
        assertThat(article.isFavorite(username)).isTrue()
    }

}
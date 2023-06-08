package com.github.io.mangjoo.realworld.article.domain

import com.github.io.mangjoo.realworld.fixture.Fixture
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArticleTest {

    private val article = Article(
        title = "test",
        description = "test",
        body = "test",
        tags = mutableSetOf("test1", "test2"),
        author = Fixture().user
    )

    @Test
    fun getTagList() {
        val tags = mutableSetOf("test1", "test2")

        assertThat(article.tags()).isEqualTo(tags)
    }

    @Test
    fun hasFavorite() {
        assertThat(article.hasFavorite()).isFalse()
    }

    @Test
    fun addFavorite() {
        article.addFavorite(userId = 1L)
        assertThat(article.favoriteCount()).isEqualTo(1)
    }

    @Test
    fun isFavorite() {
        val userId = 1L
        article.addFavorite(userId)

        assertThat(article.isFavorite(2L)).isFalse()
        assertThat(article.isFavorite(userId)).isTrue()
    }

    @Test
    fun articleUpdate() {
        val updateValue = "update"
        val update = article.update(updateValue, updateValue, updateValue)
        assertThat(update.title).isEqualTo(updateValue)
        assertThat(update.body).isEqualTo(updateValue)
        assertThat(update.description).isEqualTo(updateValue)
        assertThat(update).isEqualTo(article)
    }

    @Test
    fun unFavorite() {
        val userId = 1L
        article.addFavorite(userId)
        article.unFavorite(userId)

        assertThat(article.isFavorite(userId)).isFalse()
    }

}
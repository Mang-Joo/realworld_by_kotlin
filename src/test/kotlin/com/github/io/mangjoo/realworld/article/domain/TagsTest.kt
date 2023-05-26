package com.github.io.mangjoo.realworld.article.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TagsTest {

    @Test
    fun addTag_and_hasTag_Test() {
        val tags = Tags()
        tags.addTag("test")
        assertThat(tags.hasTag("test")).isTrue()
        assertThat(tags.hasTag("none")).isFalse()
    }

    @Test
    fun addTags_and_hasTag_Test() {
        val tags = Tags()
        tags.addTags(listOf("test", "test2"))
        assertThat(tags.hasTag("test")).isTrue()
        assertThat(tags.hasTag("test2")).isTrue()
        assertThat(tags.hasTag("none")).isFalse()
    }

    @Test
    fun removeTag_Test() {
        val tags = Tags()
        tags.addTag("test")
        assertThat(tags.hasTag("test")).isTrue()
        tags.removeTag("test")
        assertThat(tags.hasTag("test")).isFalse()
    }
}
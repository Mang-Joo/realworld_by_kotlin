package com.github.io.mangjoo.realworld.article.domain

import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable


@Embeddable
data class Tags(
    @ElementCollection
    val tags: MutableList<String> = mutableListOf()
) {
    fun addTag(tag: String) = tags.add(tag)
    fun addTags(tags: List<String>) = this.tags.addAll(tags)
    fun removeTag(tag: String) = tags.remove(tag)
    fun hasTag(tag: String) = tags.contains(tag)
}

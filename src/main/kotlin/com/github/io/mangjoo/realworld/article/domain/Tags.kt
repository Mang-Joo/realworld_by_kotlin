package com.github.io.mangjoo.realworld.article.domain

import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable


@Embeddable
data class Tags(
    @ElementCollection
    val group: MutableSet<String> = mutableSetOf()
) {
    fun addTag(tag: String) = group.add(tag)
    fun addTags(tags: List<String>) = this.group.addAll(tags)
    fun removeTag(tag: String) = group.remove(tag)
    fun hasTag(tag: String) = group.contains(tag)
}

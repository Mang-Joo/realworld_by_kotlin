package com.github.io.mangjoo.realworld.article.domain

import jakarta.persistence.*


@Embeddable
data class Tags(
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "article_tag")
    @Column(name = "tag")
    val group: MutableSet<String> = mutableSetOf()
) {
    fun addTag(tag: String) = group.add(tag)
    fun addTags(tags: List<String>) = this.group.addAll(tags)
    fun removeTag(tag: String) = group.remove(tag)
    fun hasTag(tag: String) = group.contains(tag)
}

package com.github.io.mangjoo.realworld.article.api.request

import org.springframework.data.domain.AbstractPageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class PageRequest(
    val offset: Int = 0,
    val limit: Int = 20
) : AbstractPageRequest(offset, limit) {
    override fun getSort(): Sort {
        return Sort.unsorted()
    }

    override fun next(): Pageable {
        return PageRequest(limit, offset + limit)
    }

    override fun previous(): Pageable {
        return PageRequest(limit, offset - limit)
    }

    override fun first(): Pageable {
        return PageRequest(limit, 0)
    }

    override fun withPage(pageNumber: Int): Pageable {
        return PageRequest(limit, pageNumber)
    }
}

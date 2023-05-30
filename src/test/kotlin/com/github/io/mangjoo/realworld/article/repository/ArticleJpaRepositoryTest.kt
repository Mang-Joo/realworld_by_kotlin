package com.github.io.mangjoo.realworld.article.repository

import com.github.io.mangjoo.realworld.article.domain.Article
import com.github.io.mangjoo.realworld.fixture.Fixture
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.web.servlet.MockMvc

@DataJpaTest
@Transactional
class ArticleJpaRepositoryTest(
    @Autowired private val articleJpaRepository: ArticleJpaRepository,
    @Autowired private val mockMvc: MockMvc
) {
    @Test
    fun `findAllTest`() {

        val article = Article(
            title = "title",
            description = "description",
            body = "body",
            author = Fixture().user,
            tags = mutableSetOf("tag1", "tag2")
        )
        articleJpaRepository.save(article)
        val articles: Collection<Article> =
            articleJpaRepository.findAllH(
                tag = "tag1",
                author = "author",
                favorited = null,
                pageable = PageRequest.of(0, 10)
            )
        assert(articles.size == 1)
    }
}
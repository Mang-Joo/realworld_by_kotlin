package com.github.io.mangjoo.realworld.article.repository

import com.github.io.mangjoo.realworld.article.domain.Article
import com.github.io.mangjoo.realworld.fixture.Fixture
import com.github.io.mangjoo.realworld.user.repository.UserJpaRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest

@DataJpaTest
//@Transactional
class ArticleJpaRepositoryTest(
    @Autowired private val articleJpaRepository: ArticleJpaRepository,
    @Autowired private val userRepository: UserJpaRepository
) {
    @Test
    fun `findAllTest`() {
        userRepository.save(Fixture().user)

        val article = Article(
            title = "title",
            description = "description",
            body = "body",
            author = Fixture().user,
            tags = mutableSetOf("tag1", "tag2")
        )
        articleJpaRepository.save(article)
        val articles: Collection<Article> =
            articleJpaRepository.findAll(
                tag = "tag1",
                author = Fixture().user.username,
                favorited = null,
                pageable = PageRequest.of(0, 10)
            )
        assert(articles.size == 1)
    }
}
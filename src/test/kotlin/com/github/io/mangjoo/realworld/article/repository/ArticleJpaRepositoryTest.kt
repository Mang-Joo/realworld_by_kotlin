package com.github.io.mangjoo.realworld.article.repository

import com.github.io.mangjoo.realworld.article.domain.Article
import com.github.io.mangjoo.realworld.user.domain.User
import com.github.io.mangjoo.realworld.user.domain.vo.UserInfo
import com.github.io.mangjoo.realworld.user.repository.UserJpaRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArticleJpaRepositoryTest(
    @Autowired private val articleJpaRepository: ArticleJpaRepository,
    @Autowired private val userRepository: UserJpaRepository
) {
    @Test
    fun findAllTest() {
        val user = User(
            password = "password",
            userInfo = UserInfo(
                username = "username",
                email = "email",
                bio = "bio",
                image = "image"
            )
        )
        val save = userRepository.save(user)

        val article = Article(
            title = "title",
            description = "description",
            body = "body",
            author = save,
            tags = mutableSetOf("tag1", "tag2")
        )
        articleJpaRepository.save(article)
        val articles: Collection<Article> =
            articleJpaRepository.findAll(
                tag = "tag1",
                author = user.username,
                favorited = null,
                pageable = PageRequest.of(0, 10)
            )
        assert(articles.size == 1)
    }
}
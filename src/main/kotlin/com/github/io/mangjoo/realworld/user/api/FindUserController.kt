package com.github.io.mangjoo.realworld.user.api

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Objects

@RestController
class FindUserController {

    private val logger = org.slf4j.LoggerFactory.getLogger(javaClass)

    @GetMapping("/api/user")
    fun findUser(@AuthenticationPrincipal principal: Objects) {
        logger.info("principal: $principal")
    }

}
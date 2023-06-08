package com.github.io.mangjoo.realworld.config.exception

import org.springframework.http.HttpStatus

class RealWorldException(
    val statusCode: HttpStatus,
    override val message: String,
) : RuntimeException(message) {

}
package com.github.io.mangjoo.realworld.user.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserExceptionHandler {

    @ExceptionHandler(UserException::class)
    fun handleUserDuplicationException(e: UserException): ResponseEntity<String> = ResponseEntity
        .badRequest()
        .body(e.message)
}
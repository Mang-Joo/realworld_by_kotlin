package com.github.io.mangjoo.realworld.config.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class RestControllerAdvice: ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<ProblemDetail> {
        logger.error(exception.message, exception)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR).apply {
                    detail = exception.message ?: exception.localizedMessage
                    title = HttpStatus.INTERNAL_SERVER_ERROR.name
                }
            )
    }
}
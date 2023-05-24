package com.github.io.mangjoo.realworld.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class OpenApiConfig {
    @Bean
    fun openAPI(@Value("\${springdoc.version}") springdocVersion: String?): OpenAPI =
        Info()
            .title("타이틀 입력")
            .version(springdocVersion)
            .description("API에 대한 설명 부분")
            .let { OpenAPI()
                .components(Components())
                .info(it) }

}
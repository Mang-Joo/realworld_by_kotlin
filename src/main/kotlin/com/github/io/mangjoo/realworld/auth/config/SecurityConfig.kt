package com.github.io.mangjoo.realworld.auth.config

import com.github.io.mangjoo.realworld.auth.filter.JwtLoginFilter
import com.github.io.mangjoo.realworld.auth.handler.FailureHandler
import com.github.io.mangjoo.realworld.auth.handler.SuccessHandler
import com.github.io.mangjoo.realworld.auth.provider.JwtLoginProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val jwtLoginProvider: JwtLoginProvider,
    private val successHandler: SuccessHandler,
    private val failureHandler: FailureHandler
) {

    @Bean
    fun configure(
        httpSecurity: HttpSecurity,
        authenticationManager: AuthenticationManager,
    ): SecurityFilterChain = httpSecurity
        .csrf { it.disable() }
        .cors { it.disable() }
        .sessionManagement { it.sessionCreationPolicy(STATELESS) }
        .formLogin { it.disable() }
        .authenticationProvider(jwtLoginProvider)
        .authorizeHttpRequests {
            it.requestMatchers("/api/user/login", "/swagger-ui/**", "/api-docs/**").permitAll()
                .requestMatchers(POST, "/api/user").permitAll()
                .requestMatchers(GET,
                    "/api/profiles/{username}",
                    "/api/articles",
                    "/api/articles/{slug}/comments")
                .permitAll()
                .anyRequest().authenticated()
        }
        .addFilterBefore(addJwtLoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter::class.java)
        .oauth2ResourceServer { oAuth2ResourceServerConfigurer ->
            oAuth2ResourceServerConfigurer.jwt { it.jwtAuthenticationConverter(JwtAuthenticationConverter()) }
        }
        .build()

    fun addJwtLoginFilter(authenticationManager: AuthenticationManager): JwtLoginFilter =
        JwtLoginFilter(
            AntPathRequestMatcher("/api/user/login", POST.name()),
            successHandler,
            failureHandler
        )
            .apply { setAuthenticationManager(authenticationManager) }

    @Bean
    fun authenticationManager(): AuthenticationManager = ProviderManager(listOf(jwtLoginProvider))
}
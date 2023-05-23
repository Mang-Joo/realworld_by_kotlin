package com.github.io.mangjoo.realworld.profile.api

import com.github.io.mangjoo.realworld.profile.service.GetProfile
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.web.bind.annotation.*

@RestController
class GetProfileController(
    private val getProfile: GetProfile,
    private val jwtDecoder: JwtDecoder
) {

    @GetMapping("/api/profiles/{username}")
    fun profile(
        @RequestHeader("Authorization") authorization: String?,
        @PathVariable username: String
    ) =
        when (authorization?.removePrefix("Bearer ")) {
            null -> getProfile.getProfile(username, null)
            else -> getProfile.getProfile(
                username,
                jwtDecoder.decode(authorization.removePrefix("Bearer ")).subject.toLong()
            )
        }
}
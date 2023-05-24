package com.github.io.mangjoo.realworld.profile.api

import com.fasterxml.jackson.annotation.JsonRootName
import com.github.io.mangjoo.realworld.profile.service.GetProfile
import com.github.io.mangjoo.realworld.profile.vo.Profile
import org.springframework.http.ResponseEntity
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
            null -> responseEntity(getProfile.getProfile(username, null))
            else -> {
                val id = jwtDecoder.decode(authorization.removePrefix("Bearer ")).subject
                responseEntity(getProfile.getProfile(username, id.toLong()))
            }
        }

    private fun responseEntity(it: Profile) =
        ResponseEntity.ok(GetProfileResponse(it.username, it.bio, it.image, it.following))


    @JsonRootName("profile")
    data class GetProfileResponse(
        val username: String,
        val bio: String?,
        val image: String?,
        val following: Boolean = false
    )
}
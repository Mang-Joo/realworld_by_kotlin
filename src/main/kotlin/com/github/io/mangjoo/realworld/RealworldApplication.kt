package com.github.io.mangjoo.realworld

import com.github.io.mangjoo.realworld.auth.domain.Role
import com.github.io.mangjoo.realworld.auth.domain.UserEntity
import com.github.io.mangjoo.realworld.auth.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@SpringBootApplication
class RealworldApplication

fun main(args: Array<String>) {
    runApplication<RealworldApplication>(*args)
}


@Component
class MyCommandLineRunner(
    private val passwordEncoder: PasswordEncoder,
    private val memberRepository: UserRepository
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val memberEntity = UserEntity(
            email = "mangjoo@gmail.com",
            password = passwordEncoder.encode("1234"),
            username = "mangjoo",
            bio = "bio",
            image = "image",
            isEnabled = true,
            role = Role.ROLE_USER
        )
        memberRepository.save(memberEntity)
    }
}

package kr.bistroad.authservice.token.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kr.bistroad.authservice.global.error.exception.WrongPasswordException
import kr.bistroad.authservice.token.infrastructure.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val userService: UserService,
    private val jwtSigner: JwtSigner
) {
    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    fun exchangeToken(username: String, password: String): TokenDto.ForResult {
        val user = userService.getUserByUsername(username)

        val matchesPassword = userService.verifyPassword(user.id, UserDto.VerifyPasswordReq(password))
        if (!matchesPassword) throw WrongPasswordException()

        val accessToken = publishToken(user)

        return TokenDto.ForResult(
            access_token = accessToken,
            token_type = "bearer",
            expires_in = jwtSigner.validTime
        )
    }

    fun publishToken(user: User): String {
        return jwtSigner.createToken(
            subject = objectMapper.writeValueAsString(
                TokenSubject(userId = user.id, role = user.role)
            )
        )
    }

    class TokenSubject(
        val userId: UUID,
        val role: UserRole
    )
}

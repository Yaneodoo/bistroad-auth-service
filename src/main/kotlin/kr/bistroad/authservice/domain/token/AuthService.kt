package kr.bistroad.authservice.domain.token

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kr.bistroad.authservice.domain.user.User
import kr.bistroad.authservice.domain.user.UserDto
import kr.bistroad.authservice.domain.user.UserRole
import kr.bistroad.authservice.domain.user.UserService
import kr.bistroad.authservice.exception.WrongPasswordException
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
        private val userService: UserService,
        private val jwtSigner: JwtSigner
) {
    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    fun exchangeToken(dto: TokenDto.ExchangeReq): TokenDto.ExchangeRes {
        val user = userService.getUserByUsername(dto.username)

        val matchesPassword = userService.verifyPassword(user.id, UserDto.VerifyPasswordReq(dto.password))
        if (!matchesPassword) throw WrongPasswordException()

        val accessToken = publishToken(user)

        return TokenDto.ExchangeRes(
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

package kr.bistroad.authservice.domain.token

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kr.bistroad.authservice.util.typeRef
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.*

@Service
class AuthService(
    private val jwtSigner: JwtSigner
) {
    private val restTemplate: RestTemplate = RestTemplate()
    private val objectMapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())

    fun exchangeToken(dto: TokenDto.ExchangeReq): TokenDto.ExchangeRes {
        val searchUsers = restTemplate.exchange(
            RequestEntity<List<User>>(
                HttpMethod.GET,
                URI("http://user-service:8080/users?username=${dto.username}&password=${dto.password}")
            ),
            typeRef<List<User>>()
        )
        check(searchUsers.statusCode.is2xxSuccessful) { "Failed to find the user" }
        check(!searchUsers.body.isNullOrEmpty()) { "User not found" }

        val user = searchUsers.body!!.first()
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

package kr.bistroad.authservice.token.infrastructure

import kr.bistroad.authservice.global.error.exception.UserNotFoundException
import kr.bistroad.authservice.global.util.typeRef
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.*

@Service
class UserService {
    private val restTemplate: RestTemplate = RestTemplate()

    fun getUserByUsername(username: String): User {
        try {
            val req = restTemplate.exchange(
                    RequestEntity.get(URI("http://user-service:8080/users?username=${username}"))
                            .accept(MediaType.APPLICATION_JSON).build(),
                    typeRef<List<User>>()
            )
            val users = req.body!!
            if (users.isEmpty()) throw UserNotFoundException()

            return users.first()
        } catch (ex: HttpClientErrorException.NotFound) {
            throw UserNotFoundException(ex)
        }
    }

    fun verifyPassword(id: UUID, dto: UserDto.VerifyPasswordReq): Boolean {
        try {
            val req = restTemplate.exchange(
                    RequestEntity.post(URI("http://user-service:8080/users/${id}/verify-password"))
                            .accept(MediaType.APPLICATION_JSON).body(dto),
                    Boolean::class.java
            )
            return req.body!!
        } catch (ex: HttpClientErrorException.NotFound) {
            throw UserNotFoundException(ex)
        }
    }
}
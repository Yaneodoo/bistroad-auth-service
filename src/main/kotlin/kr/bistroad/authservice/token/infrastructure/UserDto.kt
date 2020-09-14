package kr.bistroad.authservice.token.infrastructure

interface UserDto {
    data class VerifyPasswordReq(
            val password: String
    )
}
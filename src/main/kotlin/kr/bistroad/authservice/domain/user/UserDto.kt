package kr.bistroad.authservice.domain.user

interface UserDto {
    data class VerifyPasswordReq(
            val password: String
    )
}
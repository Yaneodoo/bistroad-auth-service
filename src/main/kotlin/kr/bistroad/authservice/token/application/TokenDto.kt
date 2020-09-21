package kr.bistroad.authservice.token.application

interface TokenDto {
    data class ForResult(
        val access_token: String,
        val token_type: String,
        val expires_in: Long
    ) : TokenDto
}
package kr.bistroad.authservice.token.application

interface TokenDto {
    data class ExchangeReq(
        val username: String,
        val password: String
    )

    data class ExchangeRes(
        val access_token: String,
        val token_type: String,
        val expires_in: Long?
    )
}
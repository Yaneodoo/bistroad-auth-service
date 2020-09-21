package kr.bistroad.authservice.token.presentation

interface TokenRequest {
    data class ExchangeBody(
        val username: String,
        val password: String
    )
}
package kr.bistroad.authservice.domain.token

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
        private val authService: AuthService
) {
    @PostMapping("/auth/token")
    fun postTokenExchanger(@RequestBody dto: TokenDto.ExchangeReq): TokenDto.ExchangeRes =
            authService.exchangeToken(dto)
}
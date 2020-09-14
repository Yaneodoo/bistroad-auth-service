package kr.bistroad.authservice.token.presentation

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import kr.bistroad.authservice.token.application.AuthService
import kr.bistroad.authservice.token.application.TokenDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(tags = ["/auth"])
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/auth/token")
    @ApiOperation("\${swagger.doc.operation.auth.exchange-token.description}")
    fun postTokenExchanger(@RequestBody dto: TokenDto.ExchangeReq): TokenDto.ExchangeRes =
        authService.exchangeToken(dto)
}
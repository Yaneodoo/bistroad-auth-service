package kr.bistroad.authservice.global.error.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Wrong password")
class WrongPasswordException : RuntimeException()
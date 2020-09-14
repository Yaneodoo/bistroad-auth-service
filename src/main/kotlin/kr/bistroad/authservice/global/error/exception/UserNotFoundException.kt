package kr.bistroad.authservice.global.error.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
class UserNotFoundException : RuntimeException {
    constructor() : super()
    constructor(throwable: Throwable) : super(throwable)
}
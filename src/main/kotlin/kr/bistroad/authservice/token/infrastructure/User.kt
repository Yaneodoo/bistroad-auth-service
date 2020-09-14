package kr.bistroad.authservice.token.infrastructure

import java.util.*

data class User(
    val id: UUID,
    val role: UserRole
)
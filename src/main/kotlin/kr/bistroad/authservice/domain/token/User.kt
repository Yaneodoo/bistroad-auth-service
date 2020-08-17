package kr.bistroad.authservice.domain.token

import java.util.*

data class User(
    val id: UUID,
    val role: UserRole
)
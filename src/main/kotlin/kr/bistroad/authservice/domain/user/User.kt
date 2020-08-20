package kr.bistroad.authservice.domain.user

import java.util.*

data class User(
    val id: UUID,
    val role: UserRole
)
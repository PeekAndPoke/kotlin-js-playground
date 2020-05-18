package de.peekandpoke.app.domain.login

import de.peekandpoke.app.domain.adminusers.AdminUserModel
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val user: AdminUserModel
)

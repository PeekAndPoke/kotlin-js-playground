package de.peekandpoke.app.domain.adminusers

import kotlinx.serialization.Serializable

@Serializable
data class AdminUserModel(
    val id: String,
    val name: String,
    val email: String,
    val status: AdminUserStatus,
    val roles: List<String>
)

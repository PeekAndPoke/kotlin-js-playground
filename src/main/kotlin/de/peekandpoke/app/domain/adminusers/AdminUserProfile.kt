package de.peekandpoke.app.domain.adminusers

import kotlinx.serialization.Serializable

@Serializable
data class AdminUserProfile(
    val displayName: String,
    val avatar: String? = null
)

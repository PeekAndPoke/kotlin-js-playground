package de.peekandpoke.app.domain.adminusers

import de.peekandpoke.app.domain.organisations.OrganisationModel
import kotlinx.serialization.Serializable

@Serializable
data class AdminUserModel(
    val id: String,
    val name: String,
    val email: String,
    val status: AdminUserStatus,
    val roles: Set<String>,
    val profile: AdminUserProfile,
    val org: OrganisationModel
)

fun AdminUserModel?.hasRole(role: String) = this != null && roles.contains(role)

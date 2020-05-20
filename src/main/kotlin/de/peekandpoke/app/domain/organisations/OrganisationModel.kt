package de.peekandpoke.app.domain.organisations

import kotlinx.serialization.Serializable

@Serializable
data class OrganisationModel(
    val id: String,
    val name: String
)

package de.peekandpoke.app.api

import kotlinx.serialization.Serializable

/**
 * Helper classes for query params and query bodies
 */
interface ApiQueries {

    /** Query Body for logging in with username and password */
    @Serializable
    data class LoginWithPassword(
        val user: String = "",
        val password: String = ""
    )

    /** Query Params for searching a list of entities */
    @Serializable
    data class List(
        val search: String = ""
    )

    /** Query Params for getting an entity by id */
    @Serializable
    data class GetById(
        val id: String
    )
}

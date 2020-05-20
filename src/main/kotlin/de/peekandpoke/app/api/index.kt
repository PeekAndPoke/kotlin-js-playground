package de.peekandpoke.app.api

import de.peekandpoke.jshelper.encodeURIComponent
import kotlinx.serialization.Serializable

/**
 * Appends all none empty [params] as url parameters to the given [uri]
 */
fun uri(uri: String, params: Map<String, String> = emptyMap()): String {

    val filtered = params.filter { (_, v) -> v.isNotBlank() }

    if (filtered.isEmpty()) {
        return uri
    }

    return "$uri?" + params
        .map { (k, v) -> encodeURIComponent(k) + "=" + encodeURIComponent(v) }
        .joinToString("&")
}

@Serializable
data class ApiResponse<T>(
    /** The status code */
    val status: HttpStatusCode,
    /** The response data */
    val data: T?,
    /** Messages to be sent along */
    val messages: List<Message>? = null
) {
    @Serializable
    data class HttpStatusCode(val value: Int, val description: String)

    /**
     * Message definition
     */
    @Serializable
    data class Message(val type: MessageType, val text: String)

    /**
     * Message types
     */
    @Suppress("EnumEntryName", "unused")
    enum class MessageType {
        info,
        success,
        warning,
        error
    }
}

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

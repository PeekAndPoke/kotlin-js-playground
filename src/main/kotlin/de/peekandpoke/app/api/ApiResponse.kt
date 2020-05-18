package de.peekandpoke.app.api

import kotlinx.serialization.Serializable

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

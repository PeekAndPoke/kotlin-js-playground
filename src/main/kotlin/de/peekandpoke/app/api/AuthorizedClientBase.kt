package de.peekandpoke.app.api

import de.peekandpoke.jshelper.encodeURIComponent
import de.peekandpoke.kraft.store.Stream
import de.peekandpoke.kraft.remote.remote

abstract class AuthorizedClientBase(
    private val baseUrl: String,
    private val token: Stream<String?>
) {
    protected val remote get() = remote(baseUrl).header("Authorization", "Bearer ${token()}")

    fun String.enc() = encodeURIComponent(this)
}

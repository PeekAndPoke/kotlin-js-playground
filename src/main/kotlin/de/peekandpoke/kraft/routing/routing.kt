package de.peekandpoke.kraft.routing

import de.peekandpoke.kraft.meiosis.Stream
import org.w3c.dom.events.Event
import kotlin.browser.window

interface Route {

    val pattern: String

    fun matches(uri: String): Boolean

    companion object {
        val default = StaticRoute("")
    }

    fun String.replacePlaceholder(placeholder: String, value: String) =
        replace("{$placeholder}", value)
}

data class StaticRoute(override val pattern: String) : Route {
    override fun matches(uri: String): Boolean {
        return this.pattern.trim() == uri.trim()
    }

    operator fun invoke() = "#$pattern"
}

data class ParameterizedRoute1(override val pattern: String): Route {

    companion object {
        val placeholderRegex = ":[a-zA-Z0-9]".toRegex()
    }

    private val placeholders = "\\{([^}]*)}".toRegex()
        .findAll(pattern)
        .map { it.groupValues[1] }
        // remove the special ... suffix on ktor routes
        .map { it.removeSuffix("...") }
        .toList()

    override fun matches(uri: String): Boolean {
        TODO("Not yet implemented")
    }

    operator fun invoke(p1: String) = "#$pattern".replacePlaceholder(placeholders[0], p1)
}

class Router(private val routes: List<Route>) {

    private val prefix = "#"

    val onChange = Stream<Route>(Route.default)

    init {
        window.addEventListener("DOMContentLoaded", ::windowListener)
        window.addEventListener("hashchange", ::windowListener)
    }

    fun navTo(uri: String) {
        window.location.hash = uri
    }

    private fun windowListener(event: Event) {
        event.preventDefault()

        val location = window.location.hash.removePrefix(prefix)

        val match = routes.firstOrNull { it.matches(location) }

        if (match != null) {
            onChange.next(match)
        }
    }
}


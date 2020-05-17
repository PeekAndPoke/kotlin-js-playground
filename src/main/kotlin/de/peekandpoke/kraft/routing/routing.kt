package de.peekandpoke.kraft.routing

import de.peekandpoke.kraft.meiosis.Stream
import kotlinx.html.FlowContent
import org.w3c.dom.events.Event
import kotlin.browser.window

interface Route {

    val pattern: String

    fun matches(uri: String): Boolean

    companion object {
        val default = StaticRoute("")
    }
}

data class StaticRoute(override val pattern: String) : Route {
    override fun matches(uri: String): Boolean {
        return this.pattern.trim() == uri.trim()
    }

    operator fun invoke() = "#$pattern"
}

fun routesToViews(builder: RoutesToViews.() -> Unit) = RoutesToViews().apply(builder)

class RoutesToViews {
    private val _mapping = mutableMapOf<Route, FlowContent.() -> Unit>()

    val mapping get(): Map<Route, FlowContent.() -> Unit> = _mapping.toMap()

    fun add(route: Route, view: FlowContent.() -> Unit) {
        _mapping[route] = view
    }
}

class Router(private val routes: List<Route>) {

    private val prefix = "#"
    private var route: Route = Route.default

    val onChange = Stream(route)

    init {
        window.addEventListener("DOMContentLoaded", ::windowListener)
        window.addEventListener("hashchange", ::windowListener)
    }

    private fun windowListener(event: Event) {
        event.preventDefault()

        val location = window.location.hash.removePrefix(prefix)

        val match = routes.firstOrNull { it.matches(location) }

        if (match != null) {
            route = match

            onChange.next(route)
        }
    }
}


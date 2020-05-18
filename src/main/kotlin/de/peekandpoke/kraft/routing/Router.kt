package de.peekandpoke.kraft.routing

import de.peekandpoke.kraft.meiosis.Stream
import org.w3c.dom.events.Event
import kotlin.browser.window

fun router(builder: RouterBuilder.() -> Unit) = RouterBuilder().apply(builder).build()

fun routerMiddleware(func: RouterMiddleware): RouterMiddleware = func

data class MountedRoute(
    val route: Route,
    val middlewares: List<RouterMiddleware>
)

typealias RouterMiddleware = RouterMiddlewareContext.() -> Unit

data class RouterMiddlewareContext(
    val router: Router,
    val route: Route
)

class RouterBuilder {

    private val mounted = mutableListOf<MountedRoute>()

    private val middlewares = mutableListOf<RouterMiddleware>()

    fun build() = Router(mounted.toList())

    fun mount(vararg routes: Route) {

        routes.forEach {
            mounted.add(
                MountedRoute(
                    it,
                    middlewares.toList()
                )
            )
        }
    }

    fun using(middleware: RouterMiddleware, builder: RouterBuilder.() -> Unit) {
        middlewares.add(middleware)
        builder()
        middlewares.removeLast()
    }
}

class Router(private val mountedRoutes: List<MountedRoute>) {

    private val prefix = "#"

    val onChange = Stream(MatchedRoute.default)

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

        mountedRoutes.asSequence()
            .map { mounted -> mounted.route.match(location)?.let { mounted to it } }
            .filterNotNull()
            .firstOrNull()?.let { (mounted, match) ->
                val ctx = RouterMiddlewareContext(this, match.route)

                mounted.middlewares.forEach { it(ctx) }

                onChange.next(match)
            }

        mountedRoutes.map { it.route.match(location) }.firstOrNull()?.let { match ->

        }
    }
}

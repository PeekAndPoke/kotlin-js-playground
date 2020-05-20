package de.peekandpoke.kraft.routing

import de.peekandpoke.kraft.store.Stream
import de.peekandpoke.kraft.store.WritableStream
import kotlinx.html.FlowContent
import org.w3c.dom.events.Event
import kotlin.browser.window

fun router(builder: RouterBuilder.() -> Unit) = RouterBuilder().apply(builder).build()

fun routerMiddleware(func: RouterMiddleware): RouterMiddleware = func

data class ActiveRoute(
    val matchedRoute: MatchedRoute,
    val mountedRoute: MountedRoute
) {
    val pattern = matchedRoute.pattern
    val route = matchedRoute.route

    fun render(flow: FlowContent) = mountedRoute.content(flow, matchedRoute)
}

data class MountedRoute(
    val route: Route,
    val middlewares: List<RouterMiddleware>,
    val content: FlowContent.(MatchedRoute) -> Unit
) {
    companion object {
        val default = MountedRoute(Route.default, emptyList()) {}
    }
}

typealias RouterMiddleware = RouterMiddlewareContext.() -> Unit

data class RouterMiddlewareContext(
    val router: Router,
    val route: Route
)

class RouterBuilder {

    private val mounted = mutableListOf<MountedRoute>()

    private val middlewares = mutableListOf<RouterMiddleware>()

    fun build() = Router(mounted.toList())

    fun mount(route: Route, content: FlowContent.(MatchedRoute) -> Unit) {

        mounted.add(
            MountedRoute(route, middlewares.toList(), content)
        )
    }

    fun using(middleware: RouterMiddleware, builder: RouterBuilder.() -> Unit) {
        middlewares.add(middleware)
        builder()
        middlewares.removeLast()
    }
}

class Router(private val mountedRoutes: List<MountedRoute>) {

    private val prefix = "#"

    private val _current = WritableStream(ActiveRoute(MatchedRoute.default, MountedRoute.default))
    val current: Stream<ActiveRoute> = _current

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

                _current.next(
                    ActiveRoute(match, mounted)
                )
            }
    }
}

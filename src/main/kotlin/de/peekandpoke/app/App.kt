package de.peekandpoke.app

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.routing.Route
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.a

fun Tag.app() = comp { App(it) }

class App(ctx: Ctx<Nothing?>) : Component<Nothing?, App.State>(ctx, State()) {

    data class State(
        val user: User? = null,
        val route: Route = Route.default
    )

    init {
        router.onChange { next ->
            modState {
                it.copy(route = next)
            }
        }

        userState { next ->
            modState {
                it.copy(user = next)
            }
        }
    }

    override fun VDom.render() {

        console.log("rendering app")

        ui.container {
            // Little hack to trick mithril. Otherwise it will not repaint when a route is changed.
            attributes["key"] = state.route.pattern

            if (state.route == Nav.login) {
                LoginPage()
            } else {

                ui.horizontal.list {
                    ui.item {
                        a(href = Nav.home()) { +"Home" }
                    }
                    ui.item {
                        a(href = Nav.counters()) { +"Counters" }
                    }
                    ui.item {
                        a(href = Nav.remote()) { +"Remote" }
                    }
                }

                when (state.route) {
                    Nav.home -> HomePage()
                    Nav.counters -> CountersPage()
                    Nav.remote -> RemotePage()
                    else -> HomePage()
                }
            }
        }

    }
}

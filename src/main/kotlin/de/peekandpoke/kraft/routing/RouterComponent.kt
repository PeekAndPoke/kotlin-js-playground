package de.peekandpoke.kraft.routing

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.FlowContent
import kotlinx.html.Tag
import kotlinx.html.div

fun Tag.router(router: Router, mapping: Map<Route, FlowContent.() -> Unit>) = comp(
    RouterComponent.Props(router, mapping)
) { RouterComponent(it) }

class RouterComponent(ctx: Ctx<Props>) : Component<RouterComponent.Props, RouterComponent.State>(
    ctx,
    State(Route.default)
) {
    data class Props(val router: Router, val mapping: Map<Route, FlowContent.() -> Unit>)

    data class State(
        val route: Route,
        val show: Boolean = true
    )

    init {
        props.router.onChange {
            modState { state ->
                state.copy(route = it)
            }

//            modState { state ->
//                state.copy(route = it, show = false)
//            }
//            window.setTimeout({
//                triggerRedraw()
//                modState { state ->
//                    state.copy(show = true)
//                }
//            }, 0)
        }
    }

    override fun VDom.render() {

        if (state.show) {
            val content = props.mapping[state.route]

            if (content != null) {
                div {
                    // A little hack, otherwise Mithril won't update
                    attributes["key"] = state.route.pattern
                    content()
                }
            } else {
                console.log("No view for route ${state.route}")
            }
        }
    }
}

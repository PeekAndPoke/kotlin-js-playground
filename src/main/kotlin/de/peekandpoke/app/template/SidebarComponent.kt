package de.peekandpoke.app.template

import de.peekandpoke.app.Nav
import de.peekandpoke.app.User
import de.peekandpoke.app.router
import de.peekandpoke.app.userState
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.RenderFn
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.routing.MatchedRoute
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.div

@Suppress("FunctionName")
fun Tag.Sidebar() = comp { SidebarComponent(it) }

class SidebarComponent(ctx: Ctx<Nothing?>) : Component<Nothing?, SidebarComponent.State>(ctx, State()) {

    data class State(
        val user: User? = null,
        val matchedRoute: MatchedRoute = MatchedRoute.default
    )

    init {
        router.onChange { next -> modState { it.copy(matchedRoute = next) } }

        userState { next -> modState { it.copy(user = next) } }
    }

    override fun VDom.render() {

        custom("sidebar") {

            ui.header.item {
                +"The Base"
            }

            ui.item {
                icon.user()
                +(state.user?.id ?: "")
            }

            ui.accordion {
                MenuItem(
                    state = state.matchedRoute.pattern,
                    title = {
                        div {
                            icon.building_outline()
                            +"Demo"
                        }
                    },
                    items = listOf<RenderFn>(
                        {
                            ui.item.given(state.matchedRoute.route == Nav.home) { active } A {
                                href = Nav.home()
                                +"Home"
                            }
                        }, {
                            ui.item.given(state.matchedRoute.route == Nav.counters) { active } A {
                                href = Nav.counters()
                                +"Counters"
                            }
                        }, {
                            ui.item.given(state.matchedRoute.route == Nav.remote) { active } A {
                                href = Nav.remote()
                                +"Remote"
                            }
                        }, {
                            ui.item.given(state.matchedRoute.route == Nav.orgs) { active } A {
                                href = Nav.orgs("1")
                                +"Orgs 1"
                            }
                        }, {
                            ui.item.given(state.matchedRoute.route == Nav.orgs) { active } A {
                                href = Nav.orgs("2")
                                +"Orgs 2"
                            }
                        }
                    )
                )
            }
        }
    }
}

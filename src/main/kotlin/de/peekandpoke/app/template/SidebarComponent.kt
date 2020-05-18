package de.peekandpoke.app.template

import de.peekandpoke.app.AppState
import de.peekandpoke.app.Nav
import de.peekandpoke.app.router
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.RenderFn
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.div

@Suppress("FunctionName")
fun Tag.Sidebar() = comp { SidebarComponent(it) }

class SidebarComponent(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private val user by stream(AppState.user)
    private val matchedRoute by stream(router.current)

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        custom("sidebar") {

            ui.header.item {
                +"The Base"
            }

            ui.item {
                icon.user()
                +(user?.name ?: "")
            }

            ui.accordion {

                MenuItem(
                    state = matchedRoute.pattern,
                    title = {
                        div {
                            icon.user()
                            +"Admin Users"
                        }
                    },
                    items = listOf<RenderFn> {
                        ui.item.given(matchedRoute.route == Nav.adminUsersList) { active } A {
                            href = Nav.adminUsersList()
                            +"List"
                        }
                    }
                )


                MenuItem(
                    state = matchedRoute.pattern,
                    title = {
                        div {
                            icon.building_outline()
                            +"Demo"
                        }
                    },
                    items = listOf<RenderFn>(
                        {
                            ui.item.given(matchedRoute.route == Nav.home) { active } A {
                                href = Nav.home()
                                +"Home"
                            }
                        }, {
                            ui.item.given(matchedRoute.route == Nav.counters) { active } A {
                                href = Nav.counters()
                                +"Counters"
                            }
                        }, {
                            ui.item.given(matchedRoute.route == Nav.remote) { active } A {
                                href = Nav.remote()
                                +"Remote"
                            }
                        }, {
                            ui.item.given(matchedRoute.route == Nav.orgs) { active } A {
                                href = Nav.orgs("1")
                                +"Orgs 1"
                            }
                        }, {
                            ui.item.given(matchedRoute.route == Nav.orgs) { active } A {
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

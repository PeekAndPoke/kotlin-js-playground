package de.peekandpoke.app

import de.peekandpoke.app.pages.LoginPage
import de.peekandpoke.app.pages.adminusers.AdminUsersList
import de.peekandpoke.app.template.Sidebar
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.div

fun Tag.app() = comp { App(it) }

class App(ctx: Ctx<Nothing?>) : Component<Nothing?, App.State>(ctx) {

    inner class State {
        val matchedRoute by stream(router.current)
    }

    override val state = State()

    override fun VDom.render() {

        console.log("rendering app")

        div {
            if (state.matchedRoute.route == Nav.login) {
                LoginPage()
            } else {

                div {

                    ui.sidebar.vertical.left.inverted.purple.menu.visible.fixed {
                        attributes["key"] = "__sidebar__"
                        Sidebar()
                    }

                    ui.pusher.padded.right {
                        // Little hack to trick mithril. Otherwise it will not repaint when a route is changed.
                        attributes["key"] = state.matchedRoute.pattern

                        ui.content {
                            when (state.matchedRoute.route) {
                                Nav.home -> HomePage()
                                Nav.counters -> CountersPage()
                                Nav.remote -> RemotePage()
                                Nav.orgs -> OrgsPage(state.matchedRoute.param("id"))

                                Nav.adminUsersList -> AdminUsersList()
                                else -> HomePage()
                            }
                        }
                    }
                }
            }
        }
    }
}

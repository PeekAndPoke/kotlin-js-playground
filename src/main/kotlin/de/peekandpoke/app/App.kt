package de.peekandpoke.app

import de.peekandpoke.app.pages.HomePage
import de.peekandpoke.app.pages.LoginPage
import de.peekandpoke.app.pages.adminusers.AdminUsersList
import de.peekandpoke.app.pages.demo.CountersPage
import de.peekandpoke.app.pages.demo.OrgsPage
import de.peekandpoke.app.pages.demo.RemotePage
import de.peekandpoke.app.template.Sidebar
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.div

fun Tag.app() = comp { App(it) }

class App(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private val matchedRoute by stream(router.current)

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        console.log("rendering app")

        div {
            if (matchedRoute.route == Nav.login) {
                LoginPage()
            } else {

                div {

                    ui.sidebar.vertical.left.inverted.purple.menu.visible.fixed {
                        attributes["key"] = "__sidebar__"
                        Sidebar()
                    }

                    ui.pusher.padded.right {
                        // Little hack to trick mithril. Otherwise it will not repaint when a route is changed.
                        attributes["key"] = matchedRoute.pattern

                        ui.content {
                            when (matchedRoute.route) {
                                Nav.home -> HomePage()
                                Nav.counters -> CountersPage()
                                Nav.remote -> RemotePage()
                                Nav.orgs -> OrgsPage(matchedRoute.param("id"))

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

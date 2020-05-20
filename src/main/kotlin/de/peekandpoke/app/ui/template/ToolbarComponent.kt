package de.peekandpoke.app.ui.template

import de.peekandpoke.app.AppState
import de.peekandpoke.app.Nav
import de.peekandpoke.app.mainRouter
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.noui
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.*

@Suppress("FunctionName")
fun Tag.Toolbar() = comp { ToolbarComponent(it) }

class ToolbarComponent(ctx: NoProps) : PureComponent(ctx) {

    val user by stream(AppState.user)

    override fun VDom.render() {
        div("toolbar-component") {
            ui.pointing.menu {

                noui.right.menu {

                    user?.let { user ->
                        noui.item {
                            onClick {
                                mainRouter.navTo(Nav.userProfileEditor())
                            }

                            user.profile.avatar?.let { avatar ->
                                span(classes = "avatar") {
                                    img { src = avatar }
                                }
                            }

                            span(classes = "user-name") {
                                +user.profile.displayName
                            }
                        }
                    }

                    noui.item {
                        ui.icon.input {
                            input { placeholder = "Search" }
                            icon.link.search()
                        }
                    }

                    noui.item {
                        a(href = "/") {
                            title = "Logout"
                            icon.grey.sign_out_alternate()
                        }
                    }
                }
            }
        }
    }
}

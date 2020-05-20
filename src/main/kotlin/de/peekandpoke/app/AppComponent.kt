package de.peekandpoke.app

import de.peekandpoke.app.ui.Theme
import de.peekandpoke.app.ui.pages.LoginPage
import de.peekandpoke.app.ui.template.Sidebar
import de.peekandpoke.app.ui.template.Toolbar
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.styling.css
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.css.Image
import kotlinx.css.background
import kotlinx.css.backgroundColor
import kotlinx.css.backgroundImage
import kotlinx.html.Tag
import kotlinx.html.div

fun Tag.app() = comp { AppComponent(it) }

class AppComponent(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private val activeRoute by stream(mainRouter.current)

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        console.log("rendering app ...")

        div(classes = "main") {
            if (activeRoute.matchedRoute.route == Nav.login) {
                LoginPage()
            } else {


                ui.sidebar.vertical.left.inverted.black.menu.visible.fixed {
                    css {
                        backgroundColor = Theme.Colors.dark
                        backgroundImage = Image("url(/admin-assets/images/backgrounds/noise-100x100-medium.png)")
                    }
                    attributes["key"] = "__sidebar__"
                    Sidebar()
                }

                ui.pusher.padded.right {
                    // Little hack to trick mithril. Otherwise it will not repaint when a route is changed.
                    attributes["key"] = activeRoute.pattern

                    div("toolbar") {
                        Toolbar()
                    }

                    ui.content {
                        activeRoute.render(this)
                    }
                }
            }
        }
    }
}

package de.peekandpoke.app

import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.StaticComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.*

@Suppress("FunctionName")
fun Tag.LoginPage() = comp { LoginPage(it) }

class LoginPage(ctx: Ctx<Nothing?>): StaticComponent(ctx) {

    private fun login() {
        UserActions.set(
            User("Karsten")
        )

        router.navTo(Nav.counters())
    }

    override fun VDom.render() {
        ui.container {
            style = "padding-top: 10%"

            ui.grid.centered.middle.aligned {

                ui.row {

                    ui.sixteen.wide.tablet.six.wide.computer.column {

                        ui.form {

                            ui.field {
                                label { attributes["for"] = "user"; +"User" }
                                textInput { name = "user" }
                            }

                            ui.field {
                                label { attributes["for"] = "password"; +"Password" }
                                textInput {
                                    type = InputType.password
                                    name = "password"
                                }
                            }

                            ui.wide.button Submit {
                                +"Login"
                                onClick { login() }
                            }
                        }

                    }
                }
            }
        }

    }
}

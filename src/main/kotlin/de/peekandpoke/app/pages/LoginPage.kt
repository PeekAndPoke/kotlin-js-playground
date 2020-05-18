package de.peekandpoke.app.pages

import de.peekandpoke.app.Api
import de.peekandpoke.app.AppState
import de.peekandpoke.app.Nav
import de.peekandpoke.app.forms.PasswordField
import de.peekandpoke.app.forms.TextField
import de.peekandpoke.app.forms.validation.NotBlank
import de.peekandpoke.app.forms.validation.NotEmpty
import de.peekandpoke.app.router
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.Tag
import kotlinx.html.label
import kotlinx.html.style

@Suppress("FunctionName")
fun Tag.LoginPage() = comp { LoginPage(it) }


class LoginPage(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var user by property("")
    private var password by property("")

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        ui.container {
            style = "padding-top: 10%"

            ui.grid.centered.middle.aligned {

                ui.row {

                    ui.sixteen.wide.tablet.six.wide.computer.column {

                        ui.form {

                            ui.field {
                                label { attributes["for"] = "user"; +"User" }
                                TextField(::user) {
                                    accepts(NotEmpty, NotBlank)
                                }
                            }

                            ui.field {
                                label { attributes["for"] = "password"; +"Password" }
                                PasswordField(::password)
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

    private fun login() {

        GlobalScope.launch {
            Api.login.login(user, password).collect { result ->
                // Set the user in the AppState
                AppState.UserActions.set(result.data!!.user)
                // Set the auth token in the AppState
                AppState.AuthTokenActions.set(result.data.token)

                // Navigate to the homepage
                router.navTo(Nav.home())
            }
        }
    }
}

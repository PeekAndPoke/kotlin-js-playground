package de.peekandpoke.app.pages

import de.peekandpoke.app.Api
import de.peekandpoke.app.AppState
import de.peekandpoke.app.Nav
import de.peekandpoke.app.forms.PasswordField
import de.peekandpoke.app.forms.TextField
import de.peekandpoke.app.forms.validation.NotBlank
import de.peekandpoke.app.forms.validation.NotEmpty
import de.peekandpoke.app.router
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
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


class LoginPage(ctx: Ctx<Nothing?>) : Component<Nothing?, LoginPage.State>(ctx) {

    inner class State {
        var user by property("")
        var password by property("")
    }

    override val state = State()

    private fun login() {

        GlobalScope.launch {
            Api.login.login(state.user, state.password).collect { result ->
                // Set the user in the AppState
                AppState.UserActions.set(result.data!!.user)
                // Set the auth token in the AppState
                AppState.AuthTokenActions.set(result.data.token)

                // Navigate to the homepage
                router.navTo(Nav.home())
            }
        }
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
                                TextField(state::user) {
                                    accepts(NotEmpty, NotBlank)
                                }
                            }

                            ui.field {
                                label { attributes["for"] = "password"; +"Password" }
                                PasswordField(state::password)
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

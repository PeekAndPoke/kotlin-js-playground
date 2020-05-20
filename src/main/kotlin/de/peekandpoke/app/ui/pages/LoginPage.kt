package de.peekandpoke.app.ui.pages

import de.peekandpoke.app.Api
import de.peekandpoke.app.AppState
import de.peekandpoke.app.Nav
import de.peekandpoke.app.mainRouter
import de.peekandpoke.app.ui.components.forms.PasswordField
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.components.forms.validation.NotEmpty
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onSubmit
import de.peekandpoke.kraft.styling.css
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.Tag
import kotlinx.html.div

@Suppress("FunctionName")
fun Tag.LoginPage() = comp { LoginPage(it) }


class LoginPage(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var user by property("")
    private var password by property("")

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        div {
            css {
                minHeight = 100.vh
                background = "url('/admin-assets/images/backgrounds/dark-material-bg.jpg') no-repeat"
                backgroundSize = "cover"
            }

            ui.left.floated.basic.menu {
                css {
                    minHeight = 100.vh
                    width = 400.px
                    paddingTop = 100.px
                    paddingLeft = 40.px
                    paddingRight = 40.px
                }

                ui.form Form {
                    css {
                        width = 100.pct
                    }

                    onSubmit { login() }

                    ui.header H3 { +"Login" }

                    TextField(::user) {
                        label = "User"
                        accepts(NotEmpty)
                    }

                    PasswordField(::password) {
                        label = "Password"
                    }

                    ui.fluid.primary.button Submit { +"Login" }
                }
            }
        }
    }

    private fun login() {

        GlobalScope.launch {
            Api.login.login(user, password).collect { result ->
                // Set the logged in user and auth token
                AppState.Actions.logIn(result.data!!.user, result.data.token)

                // TODO: remember where the user wanted to navigate to and then go there
                // Navigate to the homepage
                mainRouter.navTo(Nav.home())
            }
        }
    }
}

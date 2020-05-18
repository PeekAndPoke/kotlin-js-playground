package de.peekandpoke.app

import de.peekandpoke.app.api.ApiQueries
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.html.Tag
import kotlinx.html.button
import kotlinx.html.pre

@Suppress("FunctionName")
fun Tag.RemotePage() = comp { RemotePageComponent(it) }

class RemotePageComponent(ctx: Ctx<Nothing?>) : Component<Nothing?, RemotePageComponent.State>(ctx) {

    inner class State {
        var response by property<Any?>(null)
    }

    override val state = State()

    override fun VDom.render() {
        custom("remote") {
            ui.header H1 { +"Remote" }

            button {
                +"APP INFO"
                onClick { loadAppInfo() }
            }

            if (state.response != null) {
                pre { +JSON.stringify(state.response, null, 2) }
            }
        }
    }

    private fun loadAppInfo() {
        GlobalScope.launch {

            Api.login.login("karsten@jointhebase.co", "joinmenow")
                .onEach { console.log(it) }
                .collect { state.response = JSON.stringify(it) }
        }
    }
}

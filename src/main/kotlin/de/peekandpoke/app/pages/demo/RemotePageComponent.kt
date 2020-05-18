package de.peekandpoke.app.pages.demo

import de.peekandpoke.app.Api
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
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

class RemotePageComponent(ctx: NoProps) : PureComponent(ctx) {

    private var response by property<Any?>(null)

    override fun VDom.render() {
        custom("remote") {
            ui.header H1 { +"Remote" }

            button {
                +"APP INFO"
                onClick { loadAppInfo() }
            }

            if (response != null) {
                pre { +JSON.stringify(response, null, 2) }
            }
        }
    }

    private fun loadAppInfo() {
        GlobalScope.launch {

            Api.login.login("karsten@jointhebase.co", "joinmenow")
                .onEach { console.log(it) }
                .collect { response = JSON.stringify(it) }
        }
    }
}

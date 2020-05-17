package de.peekandpoke.app

import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.StaticComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.remote.body
import de.peekandpoke.kraft.remote.onError
import de.peekandpoke.kraft.remote.onErrorLog
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.Tag
import kotlinx.html.button

@Suppress("FunctionName")
fun Tag.RemotePage() = comp { RemotePageComponent(it) }

class RemotePageComponent(ctx: Ctx<Nothing?>) : StaticComponent(ctx) {

    private val client = de.peekandpoke.kraft.remote.remote("http://api.jointhebase.local:8080")

    override fun VDom.render() {
        custom("remote") {
            ui.header H1 { +"Remote" }

            button {
                +"APP INFO"
                onClick {



                    GlobalScope.launch {

                        val result = client.get("app-info")
                            .onError { console.log(it) }
                            .onErrorLog().body()

                        result.collect {
                            console.log(it)
                        }
                    }
                }
            }
        }
    }
}

package de.peekandpoke.app

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.HTMLTag
import kotlinx.html.button
import kotlinx.html.h1

fun HTMLTag.helloWorld(factor: Int) = comp(HelloWorldComponent.Props(factor = factor)) {
    HelloWorldComponent(it)
}

class HelloWorldComponent(ctx: Ctx<Props>) : Component<HelloWorldComponent.Props, HelloWorldComponent.State>(
    ctx,
    State(counter = 0)
) {
    data class Props(
        val factor: Int
    )

    data class State(
        val counter: Int
    )

    override fun onRemove() {
//        console.log("onRemove", this)
    }

    override fun VDom.render() {
        custom("hello-world") {

            h1 {
                +"Hello World Component!!!"
            }

            ui.three.column.grid {
                ui.row {
                    ui.column { +"Factor: ${props.factor}" }
                    ui.column { +"Counter: ${state.counter}" }
                    ui.column { +"Result: ${state.counter * props.factor}" }
                }
                ui.row {
                    ui.column {
                        button {
                            onClick { modState { it.copy(counter = it.counter + 1) } }
                            +"+"
                        }
                        button {
                            onClick { modState { it.copy(counter = it.counter - 1) } }
                            +"-"
                        }
                    }
                }
            }
        }
    }
}

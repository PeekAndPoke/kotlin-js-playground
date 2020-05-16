package de.peekandpoke.app

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.*

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
        console.log("onRemove", this)
    }

    override fun VDom.render() {
        div {
            h1 {
                style = "color: red;"
                +"Hello World Component !"
            }

            div { +"Factor: ${props.factor}" }
            div { +"Counter: ${state.counter}" }
            div { +"Result: ${state.counter * props.factor}" }

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

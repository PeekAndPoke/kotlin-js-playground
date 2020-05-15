package de.peekandpoke.app

import de.peekandpoke.mithrilkt.components.Component
import de.peekandpoke.mithrilkt.components.Ctx
import de.peekandpoke.mithrilkt.M
import de.peekandpoke.mithrilkt.components.comp
import kotlinx.html.*
import kotlinx.html.js.onClickFunction

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

    override fun M.render() {
        div {
            h1 {
                style = "color: red;"
                +"Hello World Component !"
            }

            div { +"Factor: ${props.factor}" }
            div { +"Counter: ${state.counter}" }
            div { +"Result: ${state.counter * props.factor}" }

            button {
                onClickFunction = { modState { it.copy(counter = it.counter + 1) } }
                +"+"
            }
            button {
                onClickFunction = { modState { it.copy(counter = it.counter - 1) } }
                +"-"
            }
        }
    }
}

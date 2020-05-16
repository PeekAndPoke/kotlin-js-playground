package de.peekandpoke.app

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.*

fun Tag.container() = comp { ContainerComponent(it) }

class ContainerComponent(ctx: Ctx<Nothing?>) : Component<Nothing?, ContainerComponent.State>(
    ctx,
    State()
) {

    data class State(
        val factor: Int = 1,
        val numItems: Int = 3
    )

    override fun VDom.render() {

        div {
            h1 { +"Container Component" }

            h2 { +"Factor" }
            button { +"+"; onClick { modState { it.copy(factor = it.factor + 1) } } }

            h2 { +"Num Items" }
            button { +"+"; onClick { modState { it.copy(numItems = it.numItems + 1) } } }
            button { +"-"; onClick { modState { it.copy(numItems = it.numItems - 1) } } }

            pre {
                +state.toString()
            }

            repeat(state.numItems) {
                helloWorld((it + 1) * state.factor)
            }
        }
    }
}

package de.peekandpoke.app

import de.peekandpoke.mithrilkt.components.Component
import de.peekandpoke.mithrilkt.components.Ctx
import de.peekandpoke.mithrilkt.M
import de.peekandpoke.mithrilkt.components.comp
import kotlinx.html.*
import kotlinx.html.js.onClickFunction

fun Tag.container() = comp { ContainerComponent(it) }

class ContainerComponent(ctx: Ctx<Nothing?>) : Component<Nothing?, ContainerComponent.State>(
    ctx,
    State()
) {

    data class State(
        val factor: Int = 1,
        val numItems: Int = 3
    )

    override fun M.render() {
        div {
            h1 { +"Container" }

            h2 { +"Factor" }
            button { +"+"; onClickFunction = { modState { it.copy(factor = it.factor + 1) } } }

            h2 { +"Num Items" }
            button { +"+"; onClickFunction = { modState { it.copy(numItems = it.numItems + 1) } } }
            button { +"-"; onClickFunction = { modState { it.copy(numItems = it.numItems - 1) } } }

            pre {
                +state.toString()
            }

            repeat(state.numItems) {
                helloWorld((it + 1) * state.factor)
            }
        }
    }
}

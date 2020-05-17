package de.peekandpoke.app

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.*

@Suppress("FunctionName")
fun Tag.CountersPage() = comp { CountersPageComponent(it) }

class CountersPageComponent(ctx: Ctx<Nothing?>): Component<Nothing?, CountersPageComponent.State>(ctx, State()) {

    data class State(
        val factor: Int = 1,
        val numItems: Int = 3
    )

    override fun VDom.render() {
        custom("counters") {
            h1 { +"Counters" }

            h2 { +"Factor" }
            button { +"+"; onClick { modState { it.copy(factor = it.factor + 1) } } }

            h2 { +"Num Items" }
            button { +"+"; onClick { modState { it.copy(numItems = it.numItems + 1) } } }
            button { +"-"; onClick { modState { it.copy(numItems = it.numItems - 1) } } }

            pre {
                +state.toString()
            }

            ui.grid {
                repeat(state.numItems) {
                    ui.row {
                        helloWorld((it + 1) * state.factor)
                    }
                }
            }
        }
    }
}

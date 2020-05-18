package de.peekandpoke.app

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.button
import kotlinx.html.h1
import kotlinx.html.h2

@Suppress("FunctionName")
fun Tag.CountersPage() = comp { CountersPageComponent(it) }

class CountersPageComponent(ctx: Ctx<Nothing?>) : Component<Nothing?, CountersPageComponent.State>(ctx) {

    inner class State {
        var factor by property(1)
        var numItems by property(3)
    }

    override val state = State()

    override fun VDom.render() {
        custom("counters") {
            h1 { +"Counters" }

            ui.grid {

                ui.row {
                    ui.header H2 { +"Factor ${state.factor}" }
                    button { +"+"; onClick { state.factor++ } }
                }

                ui.row {
                    ui.header H2 { +"Num Items  ${state.numItems}" }
                    button { +"+"; onClick { state.numItems += 10 } }
                    button { +"-"; onClick { state.numItems -= 10 } }
                }

                repeat(state.numItems) {
                    ui.row {
                        helloWorld((it + 1) * state.factor)
                    }
                }
            }
        }
    }
}

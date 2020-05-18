package de.peekandpoke.app.pages.demo

import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.button
import kotlinx.html.h1

@Suppress("FunctionName")
fun Tag.CountersPage() = comp { CountersPageComponent(it) }

class CountersPageComponent(ctx: NoProps) : PureComponent(ctx) {

    private var factor by property(1)
    private var numItems by property(3)

    override fun VDom.render() {
        custom("counters") {
            h1 { +"Counters" }

            ui.grid {

                ui.row {
                    ui.header H2 { +"Factor $factor" }
                    button { +"+"; onClick { factor++ } }
                }

                ui.row {
                    ui.header H2 { +"Num Items  $numItems" }
                    button { +"+"; onClick { numItems += 10 } }
                    button { +"-"; onClick { numItems -= 10 } }
                }

                repeat(numItems) {
                    ui.row {
                        helloWorld((it + 1) * factor)
                    }
                }
            }
        }
    }
}

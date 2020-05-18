package de.peekandpoke.app.pages.demo

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

fun HTMLTag.helloWorld(factor: Int) = comp(
    HelloWorldComponent.Props(
        factor = factor
    )
) {
    HelloWorldComponent(it)
}

class HelloWorldComponent(ctx: Ctx<Props>) : Component<HelloWorldComponent.Props>(ctx) {

    data class Props(
        val factor: Int
    )

    private var counter by property(0)

    override fun VDom.render() {
        custom("hello-world") {

            h1 {
                +"Hello World Component!!!"
            }

            ui.three.column.grid {
                ui.row {
                    ui.column { +"Factor: ${props.factor}" }
                    ui.column { +"Counter: ${counter}" }
                    ui.column { +"Result: ${counter * props.factor}" }
                }
                ui.row {
                    ui.column {
                        button {
                            +"+"
                            onClick { counter++ }
                        }
                        button {
                            +"-"
                            onClick { counter-- }
                        }
                    }
                }
            }
        }
    }
}

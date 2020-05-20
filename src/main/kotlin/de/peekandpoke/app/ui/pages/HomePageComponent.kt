package de.peekandpoke.app.ui.pages

import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.noui
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.classes

@Suppress("FunctionName")
fun Tag.HomePage() = comp { HomePageComponent(it) }

class HomePageComponent(ctx: Ctx<Nothing?>) : PureComponent(ctx) {

    override fun VDom.render() {
        custom("home") {

            ui.basic.inverted.padded.blue.segment.with("page-header") {
                ui.header H1 { +"Welcome to The Base" }
            }

            ui.basic.segment {
                ui.four.column.grid {
                    repeat(16) {
                        ui.column {
                            ui.segment {
                                ui.placeholder {
                                    noui.image.header {
                                        noui.line {}
                                        noui.line {}
                                    }
                                    noui.paragraph {
                                        noui.short.line {}
                                        noui.medium.line {}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

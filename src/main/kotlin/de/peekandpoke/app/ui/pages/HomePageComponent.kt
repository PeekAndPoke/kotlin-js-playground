package de.peekandpoke.app.ui.pages

import de.peekandpoke.app.ui.Theme
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.styling.css
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.div

@Suppress("FunctionName")
fun Tag.HomePage() = comp { HomePageComponent(it) }

class HomePageComponent(ctx: Ctx<Nothing?>) : PureComponent(ctx) {

    override fun VDom.render() {
        custom("home") {

            ui.basic.inverted.padded.blue.segment {
                css(Theme.Pages.headerPadding)
                ui.header H2 { +"Welcome to The Base" }
            }

            repeat(1000) {
                div { +"Lorem Ipsum" }
            }
        }
    }
}
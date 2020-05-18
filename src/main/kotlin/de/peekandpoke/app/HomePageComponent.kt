package de.peekandpoke.app

import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.HomePage() = comp { HomePageComponent(it) }

class HomePageComponent(ctx: Ctx<Nothing?>) : PureComponent(ctx) {

    override fun VDom.render() {
        custom("home") {
            ui.header H1 { +"Welcome" }
        }
    }
}

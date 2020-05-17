package de.peekandpoke.app

import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.StaticComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag

fun Tag.home() = comp { HomeComponent(it) }

class HomeComponent(ctx: Ctx<Nothing?>): StaticComponent(ctx) {

    override fun VDom.render() {
        custom("home") {
            ui.header H1 { +"Welcome" }
        }
    }
}

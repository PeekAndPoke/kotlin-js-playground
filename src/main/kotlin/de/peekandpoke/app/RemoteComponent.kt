package de.peekandpoke.app

import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.StaticComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag

fun Tag.remote() = comp { RemoteComponent(it) }

class RemoteComponent(ctx: Ctx<Nothing?>): StaticComponent(ctx) {

    override fun VDom.render() {
        custom("remote") {
            ui.header H1 { +"Remote" }
        }
    }
}

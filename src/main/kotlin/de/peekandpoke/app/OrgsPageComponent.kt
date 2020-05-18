package de.peekandpoke.app

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.div

@Suppress("FunctionName")
fun Tag.OrgsPage(org: String) = comp(OrgsPageComponent.Props(org = org)) { OrgsPageComponent(it) }

class OrgsPageComponent(ctx: Ctx<Props>) : Component<OrgsPageComponent.Props, Nothing?>(ctx) {

    data class Props(
        val org: String
    )

    override val state = null

    override fun VDom.render() {
        div {
            ui.header H1 { +"Welcome to organisation ${props.org}" }
        }
    }
}

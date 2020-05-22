package de.peekandpoke.app.ui.components

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.RenderFn
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.FlowContent
import kotlinx.html.Tag
import kotlinx.html.div
import kotlinx.html.style
import org.w3c.dom.HTMLElement

@Suppress("FunctionName")
fun Tag.Collapsable(builder: CollapsableComponent.Builder.() -> Unit) =
    comp(CollapsableComponent.Builder().apply(builder).build()) { CollapsableComponent(it) }

class CollapsableComponent(ctx: Ctx<Props>) : Component<CollapsableComponent.Props>(ctx) {

    data class Props(
        val header: FlowContent.(HeaderCtx) -> Unit,
        val content: RenderFn,
        val collapsed: Boolean
    )

    class Builder(
        var header: FlowContent.(HeaderCtx) -> Unit = {},
        var content: RenderFn = {},
        var collapsed: Boolean = true
    ) {
        internal fun build() = Props(header, content, collapsed)

        fun header(block: FlowContent.(HeaderCtx) -> Unit) {
            header = block
        }

        fun content(block: RenderFn) {
            content = block
        }
    }

    data class HeaderCtx(
        val collapsed: Boolean,
        val toggle: () -> Unit
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var collapsed by property(props.collapsed)

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        val scrollHeight = (dom?.lastChild as? HTMLElement)?.scrollHeight

        div {
            div {
                props.header(this, HeaderCtx(collapsed) { collapsed = !collapsed })
//                if (!collapsed) {
//                    style = "margin-bottom: 14px;"
//                }
            }

            div {
                if (!collapsed) {
                    if (scrollHeight != null) {
                        style = "max-height: ${scrollHeight}px; transition: max-height 0.2s ease-in; overflow: hidden;"
                    }
                } else {
                    style = "max-height: 0; transition: max-height 0.2s ease-out; overflow: hidden;"
                }

                props.content(this)
            }
        }
    }
}

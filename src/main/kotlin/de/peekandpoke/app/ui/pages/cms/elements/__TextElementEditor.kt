package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.CmsElement
import de.peekandpoke.app.domain.cms.elements.TextElement
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.p

@Suppress("FunctionName")
fun Tag.XxxElementEditor(item: TextElement, onChange: (CmsElement) -> Unit) =
    comp(XxxElementEditor.Props(item, onChange)) { XxxElementEditor(it) }

class XxxElementEditor(ctx: Ctx<Props>): Component<XxxElementEditor.Props>(ctx) {

    data class Props(
        val item: TextElement,
        val onChange: (CmsElement) -> Unit
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var draft by property(props.item) { props.onChange(it) }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        ui.segment {
            ui.header H5 { +props.item.elementDescription }
        }
    }
}

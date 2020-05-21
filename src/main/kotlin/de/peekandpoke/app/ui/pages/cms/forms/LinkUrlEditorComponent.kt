package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.domain.cms.LinkUrl
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.LinkUrlEditor(label: String, item: LinkUrl, onChange: (LinkUrl) -> Unit) =
    comp(LinkUrlEditorComponent.Props(label, item, onChange)) { LinkUrlEditorComponent(it) }

class LinkUrlEditorComponent(ctx: Ctx<Props>) : Component<LinkUrlEditorComponent.Props>(ctx) {

    data class Props(
        val label: String,
        val item: LinkUrl,
        val onChange: (LinkUrl) -> Unit
    )

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        props.item.apply {
            // TODO: check if the uri is valid
            TextField(url, { props.onChange(copy(url = it)) }) {
                label = props.label
            }
        }
    }
}

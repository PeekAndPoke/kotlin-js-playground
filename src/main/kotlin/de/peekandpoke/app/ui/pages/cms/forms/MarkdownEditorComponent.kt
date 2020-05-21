package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.domain.cms.Markdown
import de.peekandpoke.app.ui.components.forms.TextAreaField
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.MarkdownEditor(label: String, item: Markdown, onChange: (Markdown) -> Unit) =
    comp(MarkdownEditorComponent.Props(label, item, onChange)) { MarkdownEditorComponent(it) }

class MarkdownEditorComponent(ctx: Ctx<Props>) : Component<MarkdownEditorComponent.Props>(ctx) {

    data class Props(
        val label: String,
        val item: Markdown,
        val onChange: (Markdown) -> Unit
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var draft by property(props.item) { props.onChange(it) }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        draft.apply {
            TextAreaField({ content }, { draft = copy(content = it) }) {
                label = props.label
            }
        }
    }
}

package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.CmsElement
import de.peekandpoke.app.domain.cms.elements.TextElement
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.pages.cms.forms.ElementPaddingEditor
import de.peekandpoke.app.ui.pages.cms.forms.ElementStyleEditor
import de.peekandpoke.app.ui.pages.cms.forms.MarkdownEditor
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.TextElementEditor(item: TextElement, onChange: (CmsElement) -> Unit) =
    comp(TextElementEditor.Props(item, onChange)) { TextElementEditor(it) }

class TextElementEditor(ctx: Ctx<Props>) : Component<TextElementEditor.Props>(ctx) {

    data class Props(
        val item: TextElement,
        val onChange: (CmsElement) -> Unit
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var draft by property(props.item) { props.onChange(it) }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        draft.apply {
            ui.segment {
                ui.header H5 { +props.item.elementDescription }

                ui.form {
                    ui.four.fields {
                        ElementStyleEditor(styling) { draft = copy(styling = it) }
                        ElementPaddingEditor(padding) { draft = copy(padding = it) }
                    }

                    TextField({ headline }, { draft = copy(headline = it) }) {
                        label = "Headline"
                    }

                    MarkdownEditor("Text", text) { draft = copy(text = it) }
                }
            }
        }
    }
}

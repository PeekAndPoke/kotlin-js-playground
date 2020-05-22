package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.CmsElement
import de.peekandpoke.app.domain.cms.elements.TextElement
import de.peekandpoke.app.ui.components.forms.TextAreaField
import de.peekandpoke.app.ui.pages.cms.forms.ElementPaddingEditor
import de.peekandpoke.app.ui.pages.cms.forms.ElementStyleEditor
import de.peekandpoke.app.ui.pages.cms.forms.MarkdownEditor
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.TextElementEditor(item: TextElement, onChange: (CmsElement) -> Unit) =
    comp(TextElementEditor.Props(item, onChange)) { TextElementEditor(it) }

class TextElementEditor(ctx: Ctx<Props>) : Component<TextElementEditor.Props>(ctx) {

    data class Props(
        val item: TextElement,
        val onChange: (CmsElement) -> Unit
    )

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        ui.form {
            styles()
            ui.divider {}
            texts()
        }
    }


    private fun FlowContent.styles() {
        props.item.apply {
            ui.four.fields {
                ElementStyleEditor(styling) { props.onChange(copy(styling = it)) }
                ElementPaddingEditor(padding) { props.onChange(copy(padding = it)) }
            }
        }
    }

    private fun FlowContent.texts() {
        props.item.apply {
            ui.three.fields {
                TextAreaField(headline, { props.onChange(copy(headline = it)) }) {
                    label = "Headline"
                }

                MarkdownEditor("Text", text) { props.onChange(copy(text = it)) }
            }
        }
    }
}

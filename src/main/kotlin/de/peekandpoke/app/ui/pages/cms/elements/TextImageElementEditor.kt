package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.CmsElement
import de.peekandpoke.app.domain.cms.elements.TextImageElement
import de.peekandpoke.app.ui.components.forms.SelectField
import de.peekandpoke.app.ui.components.forms.TextAreaField
import de.peekandpoke.app.ui.pages.cms.forms.ElementPaddingEditor
import de.peekandpoke.app.ui.pages.cms.forms.ElementStyleEditor
import de.peekandpoke.app.ui.pages.cms.forms.ImageListEditor
import de.peekandpoke.app.ui.pages.cms.forms.MarkdownEditor
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.TextImageElementEditor(item: TextImageElement, onChange: (CmsElement) -> Unit) =
    comp(TextImageElementEditor.Props(item, onChange)) { TextImageElementEditor(it) }

class TextImageElementEditor(ctx: Ctx<Props>) : Component<TextImageElementEditor.Props>(ctx) {

    data class Props(
        val item: TextImageElement,
        val onChange: (CmsElement) -> Unit
    )

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        ui.segment {
            ui.header H4 { +props.item.elementDescription }

            ui.form {
                styles()
                ui.divider {}
                texts()
                ui.divider {}
                images()
            }
        }
    }

    private fun FlowContent.styles() {
        props.item.apply {
            ui.five.fields {
                ElementStyleEditor(styling) { props.onChange(copy(styling = it)) }
                ElementPaddingEditor(padding) { props.onChange(copy(padding = it)) }

                SelectField(layout, { props.onChange(copy(layout = it)) }) {
                    label = "Layout"
                    option(TextImageElement.Layout.ImageBottom) { +"Image at bottom" }
                    option(TextImageElement.Layout.ImageLeft) { +"Image at left" }
                    option(TextImageElement.Layout.ImageRight) { +"Image at right" }
                    option(TextImageElement.Layout.ImageTop) { +"Image at top" }
                }
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

    private fun FlowContent.images() {
        props.item.apply {
            ui.header H5 { +"Images" }

            ui.four.column.grid {
                ImageListEditor(images) { props.onChange(copy(images = it)) }
            }
        }
    }
}

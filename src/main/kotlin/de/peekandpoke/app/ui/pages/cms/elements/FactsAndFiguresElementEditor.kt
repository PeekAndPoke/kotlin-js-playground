package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.CmsElement
import de.peekandpoke.app.domain.cms.elements.FactsAndFiguresElement
import de.peekandpoke.app.ui.components.forms.ListField
import de.peekandpoke.app.ui.components.forms.ListFieldComponent
import de.peekandpoke.app.ui.components.forms.TextAreaField
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.pages.cms.forms.ElementPaddingEditor
import de.peekandpoke.app.ui.pages.cms.forms.ElementStyleEditor
import de.peekandpoke.app.ui.pages.cms.forms.MarkdownEditor
import de.peekandpoke.app.ui.pages.cms.forms.leftRemoveRightButtons
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.FactsAndFiguresElementEditor(item: FactsAndFiguresElement, onChange: (CmsElement) -> Unit) =
    comp(FactsAndFiguresElementEditor.Props(item, onChange)) { FactsAndFiguresElementEditor(it) }

class FactsAndFiguresElementEditor(ctx: Ctx<Props>) : Component<FactsAndFiguresElementEditor.Props>(ctx) {

    data class Props(
        val item: FactsAndFiguresElement,
        val onChange: (CmsElement) -> Unit
    )

    override fun VDom.render() {
        ui.form {
            styles()
            ui.divider {}
            texts()
            ui.divider {}
            items()
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

    private fun FlowContent.items() {
        props.item.apply {

            ui.header H5 { +"Facts" }

            ui.five.column.grid {
                ListField(items, { props.onChange(copy(items = it)) }) {
                    renderItem { renderItem(it) }
                    renderAdd { renderAdd(it) }
                }
            }
        }
    }

    private fun FlowContent.renderItem(ctx: ListFieldComponent.ItemCtx<FactsAndFiguresElement.Item>) {
        ctx.item.apply {
            ui.column {
                ui.top.attached.segment {
                    TextField(headline, { ctx.modify(copy(headline = it)) }) {
                        label = "Headline"
                    }
                    TextField(subHeadline, { ctx.modify(copy(subHeadline = it)) }) {
                        label = "subHeadline"
                    }
                    TextField(text, { ctx.modify(copy(text = it)) }) {
                        label = "Text"
                    }
                }
                ui.bottom.attached.segment {
                    ui.basic.fluid.buttons {
                        leftRemoveRightButtons(ctx)
                    }
                }
            }
        }
    }

    private fun FlowContent.renderAdd(ctx: ListFieldComponent.AddCtx<FactsAndFiguresElement.Item>) {

        ui.column {
            ui.placeholder.raised.segment {
                ui.icon.header { icon.plus() }
                onClick { ctx.add(FactsAndFiguresElement.Item()) }
            }
        }
    }
}

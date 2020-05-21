package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.CmsElement
import de.peekandpoke.app.domain.cms.elements.HeroElement
import de.peekandpoke.app.ui.components.forms.SelectField
import de.peekandpoke.app.ui.components.forms.TextAreaField
import de.peekandpoke.app.ui.pages.cms.forms.*
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.HeroElementEditor(item: HeroElement, onChange: (CmsElement) -> Unit) =
    comp(HeroElementEditor.Props(item, onChange)) { HeroElementEditor(it) }

class HeroElementEditor(ctx: Ctx<Props>) : Component<HeroElementEditor.Props>(ctx) {

    data class Props(
        val item: HeroElement,
        val onChange: (CmsElement) -> Unit
    )

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        props.item.apply {
            ui.segment {
                ui.header H4 { +props.item.elementDescription }

                ui.form {
                    basics()
                    ui.divider {}
                    cta()
                    ui.divider {}
                    images()
                }
            }
        }
    }

    private fun FlowContent.basics() {

        props.item.apply {
            ui.four.fields {
                ElementStyleEditor(styling) { props.onChange(copy(styling = it)) }

                SelectField(layout, { props.onChange(copy(layout = it)) }) {
                    label = "Layout"
                    option(HeroElement.Layout.FullSizeImage) { +"Full Size Image" }
                    option(HeroElement.Layout.ImageRight) { +"Image on the right" }
                }

                SelectField(pattern, { props.onChange(copy(pattern = it)) }) {
                    label = "Pattern"
                    patternOptions()
                }
            }

            ui.three.fields {
                TextAreaField(headline, { props.onChange(copy(headline = it)) }) {
                    label = "Headline"
                }

                MarkdownEditor("Text", text) { props.onChange(copy(text = it)) }
            }
        }
    }

    private fun FlowContent.cta() {
        props.item.apply {
            ui.header H5 { +"CTA" }

            ui.five.fields {
                CallToActionEditor(cta) { props.onChange(copy(cta = it)) }
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

package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.CmsElement
import de.peekandpoke.app.domain.cms.elements.GalleryElement
import de.peekandpoke.app.ui.components.forms.*
import de.peekandpoke.app.ui.pages.cms.forms.*
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.styling.css
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.css.maxWidth
import kotlinx.css.pct
import kotlinx.html.FlowContent
import kotlinx.html.Tag
import kotlinx.html.img

@Suppress("FunctionName")
fun Tag.GalleryElementEditor(item: GalleryElement, onChange: (CmsElement) -> Unit) =
    comp(GalleryElementEditor.Props(item, onChange)) { GalleryElementEditor(it) }

class GalleryElementEditor(ctx: Ctx<Props>) : Component<GalleryElementEditor.Props>(ctx) {

    data class Props(
        val item: GalleryElement,
        val onChange: (CmsElement) -> Unit
    )

    override fun VDom.render() {
        ui.segment {
            ui.header H4 { +props.item.elementDescription }

            ui.form {
                styles()
                ui.divider {}
                texts()
                ui.divider {}
                cta()
                ui.divider {}
                items()
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
                    option(GalleryElement.Layout.SideBySideSlider) { +"Side by side slider" }
                    option(GalleryElement.Layout.IconSlider) { +"Icon slider" }
                    option(GalleryElement.Layout.FullWidthSlider) { +"Full width slider" }
                    option(GalleryElement.Layout.ThreeColumns) { +"Three columns gallery" }
                    option(GalleryElement.Layout.FiveColumns) { +"Five columns gallery" }
                    option(GalleryElement.Layout.ThreeColumnsIcons) { +"Three columns Icon gallery" }
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

    private fun FlowContent.cta() {
        props.item.apply {
            ui.five.fields {
                CallToActionEditor(cta) { props.onChange(copy(cta = it)) }
            }
        }
    }

    private fun FlowContent.items() {
        props.item.apply {
            ui.header H5 { +"Images" }

            ui.four.column.grid {

                ListField(items, { props.onChange(copy(items = it)) }) {
                    renderItem { renderItem(it) }
                    renderAdd { renderAdd(it) }
                }
            }
        }
    }

    private fun FlowContent.renderItem(ctx: ListFieldComponent.ItemContext<GalleryElement.Item>) {
        ctx.item.apply {
            ui.column {
                ui.top.attached.segment {
                    TextField(headline, { ctx.modify(copy(headline = it)) }) {
                        label = "Headline"
                    }

                    MarkdownEditor("Text", text) { ctx.modify(copy(text = it)) }

                    ImageEditor(image) { ctx.modify(copy(image = it)) }

                    if (image.url.isNotBlank()) {
                        img {
                            css { maxWidth = 100.pct }
                            src = image.url
                        }
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

    private fun FlowContent.renderAdd(ctx: ListFieldComponent.AddContext<GalleryElement.Item>) {

        ui.column {
            ui.placeholder.raised.segment {
                ui.icon.header { icon.plus() }
                onClick { ctx.add(GalleryElement.Item()) }
            }
        }
    }
}

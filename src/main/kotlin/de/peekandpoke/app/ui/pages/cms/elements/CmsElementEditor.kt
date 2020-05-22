package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.*
import de.peekandpoke.app.ui.components.Collapsable
import de.peekandpoke.kraft.components.*
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.Tag
import kotlinx.html.p

@Suppress("FunctionName")
fun Tag.CmsElementEditor(item: CmsElement, onChange: (CmsElement) -> Unit) =
    comp(CmsElementEditor.Props(item, onChange)) { CmsElementEditor(it) }

class CmsElementEditor(ctx: Ctx<Props>) : Component<CmsElementEditor.Props>(ctx) {

    data class Props(
        val item: CmsElement,
        val onChange: (CmsElement) -> Unit
    )

    override fun VDom.render() {

        props.apply {

            when (item) {
                is CallToActionElement -> show { CallToActionElementEditor(item, props.onChange) }
                is DividerElement -> show { DividerElementEditor(item, props.onChange) }
                is FactsAndFiguresElement -> show { FactsAndFiguresElementEditor(item, props.onChange) }
                // TODO: FooterElement
                is GalleryElement -> show { GalleryElementEditor(item, props.onChange) }
                // TODO: HeaderElement
                is HeroElement -> show { HeroElementEditor(item, props.onChange) }
                // TODO: ListAndImagesElement
                // TODO: ListElement
                // TODO: ListWithCtasElement
                // TODO: PartnerContactFormElement
                // TODO: SnippetElement
                is TextElement -> show { TextElementEditor(item, props.onChange) }
                is TextImageElement -> show { TextImageElementEditor(item, props.onChange) }

                else ->
                    ui.segment {
                        ui.header H5 { +item.elementDescription }
                        ui.error.message {
                            p { +"There is no editor available for '${item::class.simpleName}'" }
                        }
                    }
            }
        }
    }

    private fun FlowContent.show(fn: RenderFn) {
        ui.segment {
            Collapsable {
                header { ctx ->
                    ui.header H4 {
                        +props.item.elementDescription
                        onClick { ctx.toggle() }
                    }
                }
                content {
                    ui.divider {}
                    fn()
                }
            }
        }
    }
}

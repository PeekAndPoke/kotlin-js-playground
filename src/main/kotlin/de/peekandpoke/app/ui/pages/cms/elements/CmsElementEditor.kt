package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.*
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
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
                is CallToActionElement -> CallToActionElementEditor(item, props.onChange)
                is DividerElement -> DividerElementEditor(item, props.onChange)
                // TODO: FactsAndFiguresElement
                // TODO: FooterElement
                is GalleryElement -> GalleryElementEditor(item, props.onChange)
                // TODO: HeaderElement
                is HeroElement -> HeroElementEditor(item, props.onChange)
                // TODO: ListAndImagesElement
                // TODO: ListElement
                // TODO: ListWithCtasElement
                // TODO: PartnerContactFormElement
                // TODO: SnippetElement
                is TextElement -> TextElementEditor(item, props.onChange)
                is TextImageElement -> TextImageElementEditor(item, props.onChange)

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
}

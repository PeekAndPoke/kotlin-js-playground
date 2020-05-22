package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.domain.cms.Image
import de.peekandpoke.app.ui.components.forms.ListField
import de.peekandpoke.app.ui.components.forms.ListFieldComponent
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
fun Tag.ImageListEditor(images: List<Image>, onChange: (List<Image>) -> Unit) =
    comp(ImageListEditorComponent.Props(images, onChange)) { ImageListEditorComponent(it) }

class ImageListEditorComponent(ctx: Ctx<Props>) : Component<ImageListEditorComponent.Props>(ctx) {

    data class Props(
        val images: List<Image>,
        val onChange: (List<Image>) -> Unit
    )

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        ListField(items = props.images, onChange = props.onChange) {
            renderItem { renderItem(it) }

            renderAdd { renderAdd(it) }
        }
    }

    private fun FlowContent.renderItem(ctx: ListFieldComponent.ItemContext<Image>) {

        ui.column {
            ui.top.attached.segment {
                ImageEditor(ctx.item) { ctx.modify(it) }

                if (ctx.item.url.isNotBlank()) {
                    img {
                        css { maxWidth = 100.pct }
                        src = ctx.item.url
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

    private fun FlowContent.renderAdd(ctx: ListFieldComponent.AddContext<Image>) {

        ui.column {
            ui.placeholder.raised.segment {
                ui.icon.header { icon.plus() }
                onClick { ctx.add(Image()) }
            }
        }
    }
}

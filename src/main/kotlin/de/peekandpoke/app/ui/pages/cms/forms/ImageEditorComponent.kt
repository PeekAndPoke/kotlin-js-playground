package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.domain.cms.Image
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.components.forms.validation.NotBlank
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.ImageEditor(item: Image, onChange: (Image) -> Unit) =
    comp(ImageEditorComponent.Props(item, onChange)) { ImageEditorComponent(it) }

class ImageEditorComponent(ctx: Ctx<Props>) : Component<ImageEditorComponent.Props>(ctx) {

    data class Props(
        val item: Image,
        val onChange: (Image) -> Unit
    )

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        props.item.apply {
            TextField(url, { props.onChange(copy(url = it)) }) {
                label = "Url"
                accepts(NotBlank)
            }

            TextField(alt, { props.onChange(copy(alt = it)) }) {
                label = "Alt"
            }
        }
    }
}

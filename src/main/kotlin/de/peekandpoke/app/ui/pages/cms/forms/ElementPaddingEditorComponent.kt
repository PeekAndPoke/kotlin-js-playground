package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.domain.cms.ElementPadding
import de.peekandpoke.app.ui.components.forms.CheckboxField
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.ElementPaddingEditor(item: ElementPadding, onChange: (ElementPadding) -> Unit) =
    comp(ElementPaddingEditorComponent.Props(item, onChange)) { ElementPaddingEditorComponent(it) }

class ElementPaddingEditorComponent(ctx: Ctx<Props>) : Component<ElementPaddingEditorComponent.Props>(ctx) {

    data class Props(
        val item: ElementPadding,
        val onChange: (ElementPadding) -> Unit
    )

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        props.item.apply {
            CheckboxField(paddingTop, { props.onChange(copy(paddingTop = it)) }) {
                label = "Padding on top"
            }

            CheckboxField(paddingBottom, { props.onChange(copy(paddingBottom = it)) }) {
                label = "Padding on bottom"
            }
        }
    }
}

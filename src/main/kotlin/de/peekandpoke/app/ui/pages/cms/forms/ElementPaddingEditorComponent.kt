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

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var draft by property(props.item) { props.onChange(it) }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        draft.apply {
            CheckboxField({ paddingTop }, { draft = copy(paddingTop = it) }) {
                label = "Padding on top"
            }

            CheckboxField({ paddingBottom }, { draft = copy(paddingBottom = it) }) {
                label = "Padding on bottom"
            }
        }
    }
}

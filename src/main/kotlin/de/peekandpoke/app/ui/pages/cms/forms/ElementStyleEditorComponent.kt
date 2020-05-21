package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.domain.cms.ElementStyle
import de.peekandpoke.app.ui.components.forms.SelectField
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.ElementStyleEditor(item: ElementStyle, onChange: (ElementStyle) -> Unit) =
    comp(ElementStyleEditorComponent.Props(item, onChange)) { ElementStyleEditorComponent(it) }

class ElementStyleEditorComponent(ctx: Ctx<Props>) : Component<ElementStyleEditorComponent.Props>(ctx) {

    data class Props(
        val item: ElementStyle,
        val onChange: (ElementStyle) -> Unit
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var draft by property(props.item) { props.onChange(it) }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        draft.apply {
            SelectField({ textColor }, { draft = copy(textColor = it)}) {
                label = "Text color"
                colorOptions()
            }
            SelectField({ backgroundColor }, { draft = copy(backgroundColor = it)}) {
                label = "Background color"
                colorOptions()
            }
        }
    }
}

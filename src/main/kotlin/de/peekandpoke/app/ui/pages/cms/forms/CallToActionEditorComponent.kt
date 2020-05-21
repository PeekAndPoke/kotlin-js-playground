package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.domain.cms.CallToAction
import de.peekandpoke.app.ui.components.forms.CheckboxField
import de.peekandpoke.app.ui.components.forms.SelectField
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.CallToActionEditor(item: CallToAction, onChange: (CallToAction) -> Unit) =
    comp(CallToActionEditorComponent.Props(item, onChange)) { CallToActionEditorComponent(it) }

class CallToActionEditorComponent(ctx: Ctx<Props>) : Component<CallToActionEditorComponent.Props>(ctx) {

    data class Props(
        val item: CallToAction,
        val onChange: (CallToAction) -> Unit
    )

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        props.item.apply {
            TextField(text, { props.onChange(copy(text = it)) }) {
                label = "Text"
            }
            SelectField(icon, { props.onChange(copy(icon = it)) }) {
                label = "Icon"
                iconOptions()
            }
            SelectField(color, { props.onChange(copy(color = it)) }) {
                label = "Color"
                colorOptions()
            }
            CheckboxField(inverted, { props.onChange(copy(inverted = it)) }) {
                label = "Inverted"
            }

            LinkUrlEditor("Url", url) { props.onChange(copy(url = it)) }
        }
    }
}

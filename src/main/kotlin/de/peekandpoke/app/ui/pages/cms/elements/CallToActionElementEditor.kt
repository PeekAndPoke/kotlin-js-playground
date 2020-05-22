package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.CallToActionElement
import de.peekandpoke.app.domain.cms.elements.CmsElement
import de.peekandpoke.app.ui.pages.cms.forms.CallToActionEditor
import de.peekandpoke.app.ui.pages.cms.forms.ElementPaddingEditor
import de.peekandpoke.app.ui.pages.cms.forms.ElementStyleEditor
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.CallToActionElementEditor(item: CallToActionElement, onChange: (CmsElement) -> Unit) =
    comp(CallToActionElementEditor.Props(item, onChange)) { CallToActionElementEditor(it) }

class CallToActionElementEditor(ctx: Ctx<Props>) : Component<CallToActionElementEditor.Props>(ctx) {

    data class Props(
        val item: CallToActionElement,
        val onChange: (CmsElement) -> Unit
    )

    override fun VDom.render() {
        ui.form {
            styles()
            ui.divider {}
            cta()
        }
    }

    private fun FlowContent.styles() {
        props.item.apply {
            ui.four.fields {
                ElementStyleEditor(styling) { props.onChange(copy(styling = it)) }
                ElementPaddingEditor(padding) { props.onChange(copy(padding = it)) }
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
}

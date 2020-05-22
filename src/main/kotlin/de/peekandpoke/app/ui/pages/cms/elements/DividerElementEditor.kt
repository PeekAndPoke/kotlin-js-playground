package de.peekandpoke.app.ui.pages.cms.elements

import de.peekandpoke.app.domain.cms.elements.CmsElement
import de.peekandpoke.app.domain.cms.elements.DividerElement
import de.peekandpoke.app.ui.components.forms.SelectField
import de.peekandpoke.app.ui.pages.cms.forms.colorOptions
import de.peekandpoke.app.ui.pages.cms.forms.patternOptions
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.DividerElementEditor(item: DividerElement, onChange: (CmsElement) -> Unit) =
    comp(DividerElementEditor.Props(item, onChange)) { DividerElementEditor(it) }

class DividerElementEditor(ctx: Ctx<Props>) : Component<DividerElementEditor.Props>(ctx) {

    data class Props(
        val item: DividerElement,
        val onChange: (CmsElement) -> Unit
    )

    override fun VDom.render() {
        ui.segment {
            ui.header H4 { +props.item.elementDescription }

            ui.form {
                settings()
            }
        }
    }

    private fun FlowContent.settings() {

        props.item.apply {

            ui.three.fields {
                SelectField(background, { props.onChange(copy(background = it)) }) {
                    label = "Background Color"
                    colorOptions()
                }

                SelectField(height, { props.onChange(copy(height = it)) }) {
                    label = "Height"
                    option(DividerElement.Height.one) { +"one" }
                    option(DividerElement.Height.two) { +"two" }
                    option(DividerElement.Height.three) { +"three" }
                    option(DividerElement.Height.four) { +"for" }
                    option(DividerElement.Height.five) { +"five" }
                    option(DividerElement.Height.six) { +"six" }
                }

                SelectField(pattern, { props.onChange(copy(pattern = it)) }) {
                    label = "Pattern"
                    patternOptions()
                }
            }
        }
    }
}

package de.peekandpoke.app.ui.components.forms

import de.peekandpoke.app.ui.components.forms.validation.Rule
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onChange
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.SemanticTag
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.InputType
import kotlinx.html.Tag
import kotlinx.html.input
import kotlinx.html.label
import org.w3c.dom.HTMLInputElement
import kotlin.reflect.KMutableProperty0

@Suppress("FunctionName")
fun Tag.CheckboxField(
    prop: KMutableProperty0<Boolean>,
    customize: CheckboxField.PropsBuilder.() -> Unit
) = comp(
    CheckboxField.PropsBuilder({ prop.get() }, { prop.set(it) }).apply(customize).build()
) { CheckboxField(it) }

@Suppress("FunctionName")
fun Tag.CheckboxField(
    getter: () -> Boolean,
    setter: (Boolean) -> Unit,
    customize: CheckboxField.PropsBuilder.() -> Unit
) = comp(
    CheckboxField.PropsBuilder(getter, setter).apply(customize).build()
) { CheckboxField(it) }


class CheckboxField(ctx: Ctx<Props>) : Component<CheckboxField.Props>(ctx) {

    class PropsBuilder(
        private val getter: () -> Boolean,
        private val setter: (Boolean) -> Unit,
        private val accepts: MutableList<Rule<Boolean>> = mutableListOf(),
        var label: String = "",
        var appearance: SemanticTag.() -> SemanticTag = { this }
    ) {
        fun build() = Props(
            getter = getter,
            setter = setter,
            accepts = accepts,
            label = label,
            appearance = appearance
        )

        fun accepts(vararg rule: Rule<Boolean>) = apply { accepts.addAll(rule) }
    }

    data class Props(
        val getter: () -> Boolean,
        val setter: (Boolean) -> Unit,
        val accepts: List<Rule<Boolean>> = emptyList(),
        val label: String,
        val appearance: SemanticTag.() -> SemanticTag
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var errors by property<List<String>>(emptyList())

    ////  IMPL   ///////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        ui.with(props.appearance).inline.field {
            ui.toggle.checkbox {
                input {
                    type = InputType.checkBox
                    checked = props.getter()
                    value = "1"
                    onChange { onInput((it.target as HTMLInputElement).checked) }
                }
                label { +props.label }
            }

            if (errors.isNotEmpty()) {
                errors.forEach { error ->
                    ui.basic.red.pointing.label { +error }
                }
            }
        }
    }

    private fun onInput(checked: Boolean) {

        console.log("checked", checked)

        errors = props.accepts.filter { !it(checked) }.map { it.message }

        if (errors.isEmpty()) {
            send(ValidInputMessage(this))
        } else {
            send(InvalidInputMessage(this))
        }

        // finally when there are no errors, propagate the value
        if (errors.isEmpty()) {
            props.setter(checked)
        }
    }
}

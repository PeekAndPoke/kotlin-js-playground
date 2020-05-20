package de.peekandpoke.app.ui.components.forms

import de.peekandpoke.app.ui.components.forms.validation.Rule
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onKeyUp
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
fun Tag.PasswordField(
    prop: KMutableProperty0<String>, customize: InputFieldComponent.PropsBuilder.() -> Unit = {}
) = InputField(prop, InputType.password, customize)

@Suppress("FunctionName")
fun Tag.TextField(
    prop: KMutableProperty0<String>, customize: InputFieldComponent.PropsBuilder.() -> Unit = {}
) = InputField(prop, InputType.text, customize)

@Suppress("FunctionName")
fun Tag.TextField(
    getter: () -> String, setter: (String) -> Unit, customize: InputFieldComponent.PropsBuilder.() -> Unit
) = InputField(getter, setter, InputType.text, customize)

@Suppress("FunctionName")
fun Tag.InputField(
    prop: KMutableProperty0<String>,
    type: InputType,
    customize: InputFieldComponent.PropsBuilder.() -> Unit
) = comp(
    InputFieldComponent.PropsBuilder(type, { prop.get() }, { prop.set(it) }).apply(customize).build()
) { InputFieldComponent(it) }

@Suppress("FunctionName")
fun Tag.InputField(
    getter: () -> String,
    setter: (String) -> Unit,
    type: InputType,
    customize: InputFieldComponent.PropsBuilder.() -> Unit
) = comp(
    InputFieldComponent.PropsBuilder(type, getter, setter).apply(customize).build()
) { InputFieldComponent(it) }

class InputFieldComponent(ctx: Ctx<Props>) : FormFieldBase<InputFieldComponent.Props>(ctx) {

    class PropsBuilder(
        private val type: InputType,
        private val getter: () -> String,
        private val setter: (String) -> Unit,
        private val accepts: MutableList<Rule<String>> = mutableListOf(),
        var label: String = "",
        var placeholder: String = "",
        var appearance: SemanticTag.() -> SemanticTag = { this }
    ) {
        fun build() = Props(
            getter = getter,
            setter = setter,
            accepts = accepts,
            type = type,
            label = label,
            placeholder = placeholder,
            appearance = appearance
        )

        fun accepts(vararg rule: Rule<String>) = apply { accepts.addAll(rule) }
    }

    data class Props(
        override val getter: () -> String,
        override val setter: (String) -> Unit,
        override val accepts: List<Rule<String>> = emptyList(),
        val type: InputType,
        val label: String,
        val placeholder: String,
        val appearance: SemanticTag.() -> SemanticTag
    ) : FormFieldBase.Props

    override fun VDom.render() {

        ui.with(props.appearance).field {
            label {
                +props.label

                input {
                    value = input
                    placeholder = props.placeholder
                    type = props.type
                    onKeyUp { onInput((it.target as HTMLInputElement).value) }
                }
            }
            if (errors.isNotEmpty()) {
                errors.forEach { error ->
                    ui.basic.red.pointing.label { +error }
                }
            }
        }
    }
}

package de.peekandpoke.app.forms

import de.peekandpoke.app.forms.validation.Rule
import de.peekandpoke.kraft.components.*
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.InputType
import kotlinx.html.Tag
import kotlinx.html.input
import org.w3c.dom.HTMLInputElement
import kotlin.reflect.KMutableProperty0

@Suppress("FunctionName")
fun Tag.TextField(
    prop: KMutableProperty0<String>, customize: InputFieldComponent.PropsBuilder.() -> Unit = {}
) = InputField(prop, InputType.text, customize)

@Suppress("FunctionName")
fun Tag.PasswordField(prop: KMutableProperty0<String>, customize: InputFieldComponent.PropsBuilder.() -> Unit = {}) =
    InputField(prop, InputType.password, customize)

@Suppress("FunctionName")
fun Tag.InputField(
    prop: KMutableProperty0<String>,
    type: InputType,
    customize: InputFieldComponent.PropsBuilder.() -> Unit
) = comp(
    InputFieldComponent.PropsBuilder(type, { prop.get() }, { prop.set(it) }).apply(customize).build()
) { InputFieldComponent(it) }

class InputFieldComponent(ctx: Ctx<Props>) : Component<InputFieldComponent.Props, InputFieldComponent.State>(ctx) {

    class PropsBuilder(
        private val type: InputType,
        private val getter: () -> String,
        private val setter: (String) -> Unit,
        private val accepts: MutableList<Rule<String>> = mutableListOf()
    ) {
        fun build() = Props(
            type = type,
            getter = getter,
            setter = setter,
            accepts = accepts.toList()
        )

        fun accepts(vararg rule: Rule<String>) = apply { accepts.addAll(rule) }
    }

    data class Props(
        val type: InputType,
        val getter: () -> String,
        val setter: (String) -> Unit,
        val accepts: List<Rule<String>> = emptyList()
    ) {
    }

    inner class State {
        var value by property(props.getter())
        var errors by property<List<String>>(emptyList())
    }

    override val state = State()

    override fun VDom.render() {

        input {
            value = state.value
            type = props.type
//            onChange { event ->
//                val value = (event.target as HTMLInputElement).value
////                console.log(value)
//
//                state.value = value
//                props.setter(value)
////                console.log(state)
//            }

            onChange { event ->
                val value = (event.target as HTMLInputElement).value

                state.value = value

                state.errors = props.accepts
                    .filter { !it(value) }
                    .map { it.message }

                if (state.errors.isNotEmpty()) {
                    return@onChange
                }

                props.setter(value)
            }
        }

        if (state.errors.isNotEmpty()) {
            state.errors.forEach { error ->
                ui.basic.red.pointing.label { +error }
            }
        }
    }
}

package de.peekandpoke.app.ui.components.forms

import de.peekandpoke.app.ui.components.forms.validation.Rule
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onChange
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.SemanticTag
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.*
import org.w3c.dom.HTMLSelectElement
import kotlin.reflect.KMutableProperty0

@Suppress("FunctionName")
fun <T> Tag.SelectField(
    prop: KMutableProperty0<T>,
    customize: SelectField.PropsBuilder<T>.() -> Unit
) = comp(
    SelectField.PropsBuilder({ prop.get() }, { prop.set(it) }).apply(customize).build()
) { SelectField(it) }

@Suppress("FunctionName")
fun <T> Tag.SelectField(
    getter: () -> T,
    setter: (T) -> Unit,
    customize: SelectField.PropsBuilder<T>.() -> Unit
) = comp(
    SelectField.PropsBuilder(getter, setter).apply(customize).build()
) { SelectField(it) }


class SelectField<T>(ctx: Ctx<Props<T>>) : Component<SelectField.Props<T>>(ctx) {

    class PropsBuilder<T>(
        private val getter: () -> T,
        private val setter: (T) -> Unit,
        private val accepts: MutableList<Rule<String>> = mutableListOf(),
        var label: String = "",
        var appearance: SemanticTag.() -> SemanticTag = { this },
        private val options: MutableList<Option<T>> = mutableListOf()
    ) {
        fun build() = Props(
            getter = getter,
            setter = setter,
            accepts = accepts,
            label = label,
            appearance = appearance,
            options = options
        )

        fun accepts(vararg rule: Rule<String>) = apply { accepts.addAll(rule) }

        fun option(realValue: T, formValue: String, content: OPTION.() -> Unit) = apply {
            options.add(
                Option(realValue, formValue, content)
            )
        }
    }

    data class Option<T>(
        val realValue: T,
        val formValue: String,
        val content: OPTION.() -> Unit
    )

    data class Props<T>(
        val getter: () -> T,
        val setter: (T) -> Unit,
        val accepts: List<Rule<String>> = emptyList(),
        val label: String,
        val appearance: SemanticTag.() -> SemanticTag,
        val options: List<Option<T>>
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var errors by property<List<String>>(emptyList())

    ////  IMPL   ///////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        val currentValue = props.getter()

        ui.with(props.appearance).field {
            label {
                +props.label

                select {
                    onChange { onInput((it.target as HTMLSelectElement).value) }

                    props.options.forEach {
                        option {
                            value = it.formValue
                            selected = it.realValue == currentValue
                            it.content(this)
                        }
                    }
                }
            }
            if (errors.isNotEmpty()) {
                errors.forEach { error ->
                    ui.basic.red.pointing.label { +error }
                }
            }
        }
    }

    private fun onInput(input: String) {

        errors = props.accepts.filter { !it(input) }.map { it.message }

        if (errors.isEmpty()) {
            send(ValidInputMessage(this))
        } else {
            send(InvalidInputMessage(this))
        }

        // finally when there are no errors, propagate the value
        if (errors.isEmpty()) {
            props.options
                .firstOrNull { it.formValue == input }
                ?.let { props.setter(it.realValue) }
        }
    }
}

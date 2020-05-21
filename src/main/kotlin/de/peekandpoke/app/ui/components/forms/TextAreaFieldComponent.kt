package de.peekandpoke.app.ui.components.forms

import de.peekandpoke.app.ui.components.forms.validation.Rule
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onKeyUp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.SemanticTag
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.label
import kotlinx.html.textArea
import org.w3c.dom.HTMLTextAreaElement
import kotlin.reflect.KMutableProperty0

@Suppress("FunctionName")
fun Tag.TextAreaField(
    prop: KMutableProperty0<String>,
    customize: TextAreaFieldComponent.PropsBuilder.() -> Unit
) = comp(
    TextAreaFieldComponent.PropsBuilder(prop.get(), { prop.set(it) }).apply(customize).build()
) { TextAreaFieldComponent(it) }

@Suppress("FunctionName")
fun Tag.TextAreaField(
    original: String,
    onChange: (String) -> Unit,
    customize: TextAreaFieldComponent.PropsBuilder.() -> Unit
) = comp(
    TextAreaFieldComponent.PropsBuilder(original, onChange).apply(customize).build()
) { TextAreaFieldComponent(it) }

class TextAreaFieldComponent(ctx: Ctx<Props>) : FormFieldBase<TextAreaFieldComponent.Props>(ctx) {

    class PropsBuilder(
        private val original: String,
        private val onChange: (String) -> Unit,
        private val accepts: MutableList<Rule<String>> = mutableListOf(),
        var label: String = "",
        var placeholder: String = "",
        var appearance: SemanticTag.() -> SemanticTag = { this }
    ) {
        fun build() = Props(
            original = original,
            onChange = onChange,
            accepts = accepts,
            label = label,
            placeholder = placeholder,
            appearance = appearance
        )

        fun accepts(vararg rule: Rule<String>) = apply { accepts.addAll(rule) }
    }

    data class Props(
        override val original: String,
        override val onChange: (String) -> Unit,
        override val accepts: List<Rule<String>> = emptyList(),
        val label: String,
        val placeholder: String,
        val appearance: SemanticTag.() -> SemanticTag
    ) : FormFieldBase.Props

    override fun onRemove() {
        send(ValidInputMessage(this))
    }

    override fun VDom.render() {

        ui.with(props.appearance).field {
            label {
                +props.label

                textArea {
                    +props.original

                    if (props.placeholder.isNotBlank()) {
                        placeholder = props.placeholder
                    }

                    onKeyUp { validate((it.target as HTMLTextAreaElement).value) }
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

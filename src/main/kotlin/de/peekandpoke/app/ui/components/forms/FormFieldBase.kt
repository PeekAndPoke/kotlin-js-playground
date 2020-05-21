package de.peekandpoke.app.ui.components.forms

import de.peekandpoke.app.ui.components.forms.validation.Rule
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx

abstract class FormFieldBase<P : FormFieldBase.Props>(ctx: Ctx<P>) : Component<P>(ctx) {

    interface Props {
        val original: String
        val onChange: (String) -> Unit
        val accepts: List<Rule<String>>
    }

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    protected var errors by property<List<String>>(emptyList())

    ////  IMPL   ///////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onRemove() {
        send(ValidInputMessage(this))
    }

    protected fun validate(input: String) {

        props.onChange(input)

        errors = props.accepts.filter { !it(input) }.map { it.message }

        if (errors.isEmpty()) {
            send(ValidInputMessage(this))
        } else {
            send(InvalidInputMessage(this))
        }
    }
}

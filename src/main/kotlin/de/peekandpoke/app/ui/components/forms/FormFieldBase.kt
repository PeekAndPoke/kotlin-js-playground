package de.peekandpoke.app.ui.components.forms

import de.peekandpoke.app.ui.components.forms.validation.Rule
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx

abstract class FormFieldBase<P: FormFieldBase.Props>(ctx: Ctx<P>): Component<P>(ctx) {

    interface Props {
        val getter: () -> String
        val setter: (String) -> Unit
        val accepts: List<Rule<String>>
    }

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    protected var input by property(props.getter())
    protected var errors by property<List<String>>(emptyList())

    ////  IMPL   ///////////////////////////////////////////////////////////////////////////////////////////////////////

    protected fun onInput(value: String) {
        input = value

        errors = props.accepts.filter { !it(input) }.map { it.message }

        if (errors.isEmpty()) {
            send(ValidInputMessage(this))
        } else {
            send(InvalidInputMessage(this))
        }

        // finally when there are no errors, propagate the value
        if (errors.isEmpty()) {
            props.setter(input)
        }
    }
}

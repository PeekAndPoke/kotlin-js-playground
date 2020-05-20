package de.peekandpoke.app.ui.components.forms

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.MessageBase

abstract class FormComponent<PROPS>(ctx: Ctx<PROPS>): Component<PROPS>(ctx) {

    val isValid get() = invalidFields.isEmpty()

    var invalidFields by property(mutableSetOf<Component<*>>())

    init {
        events.stream {
            when (it) {
                is InvalidInputMessage -> invalidFields.add(it.sender)

                is ValidInputMessage -> invalidFields.remove(it.sender)
            }
        }
    }
}

class InvalidInputMessage(sender: Component<*>): MessageBase(sender)

class ValidInputMessage(sender: Component<*>): MessageBase(sender)

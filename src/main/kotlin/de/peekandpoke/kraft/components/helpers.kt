package de.peekandpoke.kraft.components

import de.peekandpoke.kraft.vdom.VDomTagConsumer
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.Tag
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event

/**
 * Adds a child component to the current tag
 */
fun <P> Tag.comp(props: P, component: (Ctx<P>) -> Component<P, *>) =
    (this.consumer as VDomTagConsumer).onComponent(props, component)

/**
 * Adds a parameterless child component to the current tag
 */
fun Tag.comp(component: (Ctx<Nothing?>) -> Component<Nothing?, *>) = comp(null, component)


/**
 * Easier to use onClick handler
 */
fun CommonAttributeGroupFacade.onClick(handler: (Event) -> Unit) {
    onClickFunction = handler
}

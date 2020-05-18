package de.peekandpoke.kraft.components

import de.peekandpoke.kraft.vdom.VDomTagConsumer
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.Tag
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent

typealias RenderFn = FlowContent.() -> Unit

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
 * onClick handler
 */
fun CommonAttributeGroupFacade.onClick(handler: (MouseEvent) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    onClickFunction = handler as (Event) -> Unit
}

/**
 * onSubmit handler
 */
fun CommonAttributeGroupFacade.onSubmit(handler: (Event) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    onSubmitFunction = handler as (Event) -> Unit
}

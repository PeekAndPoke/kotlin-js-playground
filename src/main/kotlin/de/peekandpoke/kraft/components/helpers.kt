package de.peekandpoke.kraft.components

import de.peekandpoke.kraft.vdom.VDomTagConsumer
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.Tag
import kotlinx.html.js.*
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent

typealias RenderFn = FlowContent.() -> Unit

/**
 * Adds a child component to the current tag
 */
fun <P> Tag.comp(props: P, component: (Ctx<P>) -> Component<P>) =
    (this.consumer as VDomTagConsumer).onComponent(props, component)

/**
 * Adds a parameterless child component to the current tag
 */
fun Tag.comp(component: (NoProps) -> Component<Nothing?>) = comp(null, component)

/**
 * onChange handler
 */
fun CommonAttributeGroupFacade.onBlur(handler: (Event) -> Unit) {
    @Suppress("UNCHECKED_CAST", "USELESS_CAST")
    onBlurFunction = handler as (Event) -> Unit
}

/**
 * onChange handler
 */
fun CommonAttributeGroupFacade.onChange(handler: (Event) -> Unit) {
    @Suppress("UNCHECKED_CAST", "USELESS_CAST")
    onChangeFunction = handler as (Event) -> Unit
}

/**
 * onClick handler
 */
fun CommonAttributeGroupFacade.onClick(handler: (MouseEvent) -> Unit) {
    @Suppress("UNCHECKED_CAST")
    onClickFunction = handler as (Event) -> Unit
}

/**
 * onChange handler
 */
fun CommonAttributeGroupFacade.onKeyDown(handler: (KeyboardEvent) -> Unit) {
    @Suppress("UNCHECKED_CAST", "USELESS_CAST")
    onKeyDownFunction = handler as (Event) -> Unit
}

/**
 * onChange handler
 */
fun CommonAttributeGroupFacade.onKeyUp(handler: (KeyboardEvent) -> Unit) {
    @Suppress("UNCHECKED_CAST", "USELESS_CAST")
    onKeyUpFunction = handler as (Event) -> Unit
}

/**
 * onSubmit handler
 */
fun CommonAttributeGroupFacade.onSubmit(handler: (Event) -> Unit) {
    @Suppress("UNCHECKED_CAST", "USELESS_CAST")
    onSubmitFunction = {
        it.preventDefault()
        handler(it)
    }
}

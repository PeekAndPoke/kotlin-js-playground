package de.peekandpoke.mithrilkt.components

import de.peekandpoke.mithrilkt.MithrilTagConsumer
import kotlinx.html.Tag

/**
 * Adds a child component to the current tag
 */
fun <P> Tag.comp(params: P, component: (Ctx<P>) -> Component<P, *>) =
    (this.consumer as MithrilTagConsumer).onComponent(params, component)

/**
 * Adds a parameterless child component to the current tag
 */
fun Tag.comp(component: (Ctx<Nothing?>) -> Component<Nothing?, *>) = comp(null, component)

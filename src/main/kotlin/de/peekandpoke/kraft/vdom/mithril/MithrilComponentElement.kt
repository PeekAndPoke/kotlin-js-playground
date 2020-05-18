package de.peekandpoke.kraft.vdom.mithril

import de.peekandpoke.jshelper.js
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.vdom.VDomElement

data class MithrilComponentElement<P>(
    val ctx: Ctx<P>,
    val component: (Ctx<P>) -> Component<P>
) : VDomElement {

    override fun render(): dynamic {
        return m(
            MithrilLowLevelComponent,
            mapOf(
                "ctx" to ctx,
                "creator" to component
            ).js,
            null
        )
    }
}

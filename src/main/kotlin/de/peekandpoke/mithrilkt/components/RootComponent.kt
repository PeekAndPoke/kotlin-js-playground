package de.peekandpoke.mithrilkt.components

import de.peekandpoke.jshelper.jsObjectOf
import de.peekandpoke.mithrilkt.M
import de.peekandpoke.mithrilkt.m
import org.w3c.dom.HTMLElement

class RootComponent(private val view: M.() -> Any): StaticComponent(Ctx.Null) {

    private val mithril get() = jsObjectOf(
        "view" to { M().render{ render() } }
    )

    fun mountAt(element: HTMLElement) {
        m.mount(element, mithril)
    }

    override fun M.render() {
        view()
    }
}

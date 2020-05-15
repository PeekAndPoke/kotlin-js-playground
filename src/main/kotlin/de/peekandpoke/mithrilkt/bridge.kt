package de.peekandpoke.mithrilkt

import org.w3c.dom.HTMLElement

external object m {
    fun render(element: HTMLElement, content: dynamic): dynamic

    fun mount(element: HTMLElement, component: dynamic): dynamic

    fun redraw(): dynamic
}

external fun m(tag: String, content: dynamic): Any
external fun m(tag: dynamic, attrs: dynamic, content: dynamic): Any

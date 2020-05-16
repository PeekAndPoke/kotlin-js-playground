package de.peekandpoke.kraft.vdom.mithril

import de.peekandpoke.jshelper.js
import de.peekandpoke.kraft.vdom.VDomElement

data class MithrilRootElement(
    val children: MutableList<VDomElement> = mutableListOf()
) : VDomElement {
    override fun appendChild(child: VDomElement) {
        children.add(child)
    }

    override fun render(): dynamic {
        return children.map { it.render() }.js
    }
}

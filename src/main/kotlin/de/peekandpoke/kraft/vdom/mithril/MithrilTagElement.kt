package de.peekandpoke.kraft.vdom.mithril

import de.peekandpoke.jshelper.js
import de.peekandpoke.kraft.vdom.VDomElement
import kotlinx.html.Tag
import org.w3c.dom.events.Event

data class MithrilTagElement(
    val tag: Tag,
    val children: MutableList<VDomElement> = mutableListOf(),
    val events: MutableMap<String, (Event) -> Any?> = mutableMapOf()
) : VDomElement {
    override fun appendChild(child: VDomElement) {
        children.add(child)
    }

    override fun addEvent(name: String, callback: (Event) -> Any?) {
        events[name] = callback
    }

    override fun render(): dynamic {

        // get the attrs at plain de.peekandpoke.jshelper.getJs object
        val attrs = tag.attributes.js
        // merge the events into the attributes
        events.forEach { (k, v) -> attrs[k] = v }

//        console.log(tag.tagName, "attrs", attrs)

        // Render with mithril
        return m(
            tag.tagName,
            attrs,
            children.map { it.render() }.js
        )
    }
}

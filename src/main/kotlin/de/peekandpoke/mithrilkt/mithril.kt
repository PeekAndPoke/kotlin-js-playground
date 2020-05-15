package de.peekandpoke.mithrilkt

import de.peekandpoke.jshelper.js
import de.peekandpoke.mithrilkt.components.Component
import de.peekandpoke.mithrilkt.components.Ctx
import de.peekandpoke.mithrilkt.renderer.LowLevelComponent
import kotlinx.html.*
import org.w3c.dom.events.Event


class M(host: Component<*, *>? = null) : FlowContent {

    fun render(builder: M.() -> Any?): dynamic {
        builder()
        return consumer.finalize().render()
    }

    override val consumer = MithrilTagConsumer(host)

    override val attributes: MutableMap<String, String>
        get() = mutableMapOf()

    override val attributesEntries: Collection<Map.Entry<String, String>>
        get() = emptyList()

    override val emptyTag: Boolean = false

    override val inlineTag: Boolean = false

    override val namespace: String? = null

    override val tagName: String = ""
}

interface MithrilElement {
    fun appendChild(child: MithrilElement) {}

    fun addEvent(name: String, callback: (Event) -> Any?) {}

    fun render(): dynamic
}

data class MithrilRootElement(
    val children: MutableList<MithrilElement> = mutableListOf()
) : MithrilElement {
    override fun appendChild(child: MithrilElement) {
        children.add(child)
    }

    override fun render(): dynamic {
        return children.map { it.render() }.js
    }
}

data class MithrilContentElement(
    val content: CharSequence
) : MithrilElement {
    override fun render(): dynamic {
        return content
    }
}


data class MithrilComponentElement<P>(
    val ctx: Ctx<P>,
    val component: (Ctx<P>) -> Component<P, *>
) : MithrilElement {

    override fun render(): dynamic {
        return m(
            LowLevelComponent,
            mapOf(
                "ctx" to ctx,
                "creator" to component
            ).js,
            null
        )
    }
}

data class MithrilTagElement(
    val tag: Tag,
    val children: MutableList<MithrilElement> = mutableListOf(),
    val events: MutableMap<String, (Event) -> Any?> = mutableMapOf()
) : MithrilElement {
    override fun appendChild(child: MithrilElement) {
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

class MithrilTagConsumer(private val host: Component<*, *>? = null) : TagConsumer<MithrilElement> {

    private val root = MithrilRootElement()

    private val stack = mutableListOf<MithrilElement>(
        root
    )

    fun <P> onComponent(params: P, component: (Ctx<P>) -> Component<P, *>) {
//        console.log(component)

        stack.last().appendChild(
            MithrilComponentElement(
                Ctx(host, params),
                component
            )
        )
    }

    override fun finalize(): MithrilElement {
//        console.log("finalize")

        return stack.first()
    }

    override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) {
//        console.log("onTagAttributeChange", tag, attribute, value)
    }

    override fun onTagComment(content: CharSequence) {
        console.log("onTagComment", content)
    }

    override fun onTagContent(content: CharSequence) {
//        console.log("onTagContent", content)

        stack.last().appendChild(
            MithrilContentElement(content)
        )
    }

    override fun onTagContentEntity(entity: Entities) {
//        console.log("onTagContentEntity", entity)
    }

    override fun onTagContentUnsafe(block: Unsafe.() -> Unit) {
//        console.log("onTagContentUnsafe", block)
    }

    override fun onTagEnd(tag: Tag) {
//        console.log("onTagEnd", tag)

        stack.removeAt(stack.size - 1)
    }

    override fun onTagEvent(tag: Tag, event: String, value: (Event) -> Unit) {
//        console.log("onTagEvent", tag, event, value)

        stack.last().addEvent(event, value)
    }

    override fun onTagStart(tag: Tag) {
//        console.log("onTagStart", tag)

        val element = MithrilTagElement(
            tag = tag
        )

        stack.last().appendChild(element)

        stack.add(element)
    }
}

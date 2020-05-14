import kotlinx.html.Entities
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.Unsafe
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event

external object m {
    fun render(element: HTMLElement, content: dynamic): dynamic

    fun mount(element: HTMLElement, component: dynamic): dynamic
}

external fun m(tag: String, content: dynamic): Any
external fun m(tag: dynamic, attrs: dynamic, content: dynamic): Any


val mithril: MithrilTagConsumer
    get() = MithrilTagConsumer()

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

val LowLevelComponent = mapOf(
    "oninit" to { vnode: dynamic ->
//        console.log("oninit", vnode)

        if (!vnode.state.instance) {
            vnode.state.instance = vnode.attrs.creator()
        }

//        console.log(vnode.state)
    },

    "oncreate" to { vnode: dynamic ->
//        console.log("oncreate", vnode)
    },

    "view" to { vnode: dynamic ->
//        console.log("VNODE", vnode)

        vnode.state.instance.view().render()
    }
).js

data class MithrilComponentElement(
    val component: () -> Component
) : MithrilElement {

    override fun render(): dynamic {
        return m(
            LowLevelComponent,
            mapOf(
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

        // get the attrs at plain js object
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

class MithrilTagConsumer : TagConsumer<MithrilElement> {

    private val root = MithrilRootElement()

    private val stack = mutableListOf<MithrilElement>(
        root
    )

    fun onComponent(component: () -> Component) {
//        console.log(component)

        stack.last().appendChild(
            MithrilComponentElement(component)
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

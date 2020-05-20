package de.peekandpoke.kraft.components

import de.peekandpoke.kraft.store.Stream
import de.peekandpoke.kraft.vdom.VDom
import org.w3c.dom.Element
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Base class of all Components
 */
@Suppress("FunctionName")
abstract class Component<PROPS>(val ctx: Ctx<PROPS>) {

    /** The parent component */
    private val parent: Component<*>? = ctx.parent
    /** Backing field for the Props */
    private var _props: PROPS = ctx.props
    /** Flag indicating if the component needs a redraw */
    private var needsRedraw = true
    /** Render cache with the last render result */
    private var renderCache: Any? = null
    /** A list of stream unsubscribe functions. Will be call when the component is removed */
    private val unSubscribers = mutableListOf<() -> Unit>()

    /** Public getter for the Props */
    val props: PROPS get() = _props
    /** Message system for child to parent component communication */
    val events = Messages()
    /** The Dom node to which the component is rendered */
    var dom: Element? = null

    /**
     * Every component needs to implement this method
     */
    abstract fun VDom.render()

    /**
     * Every component can react when it is removed
     */
    open fun onRemove() {
    }

    /**
     * Returns 'true' when the component should redraw.
     *
     * By default returns [props] != [nextProps]
     */
    open fun shouldRedraw(nextProps: PROPS): Boolean {
//        console.log("nextProps", this, nextProps, props, nextProps != props)
        return props != nextProps
    }

    /**
     * Triggers a redraw
     */
    fun triggerRedraw() {
        // triggering a redraw for all parent components
        _triggerRedrawRecursive()
    }

    /**
     * Sends a message to all parent components in the tree
     */
    fun send(message: Message) {
        // We do not dispatch the message on the component that sent it
        if (message.sender != this) {
            events.send(message)
        }
        // Continue with the parent if the message was not stopped
        if (parent != null && !message.isStopped) {
            parent.send(message)
        }
    }

    ////  Protected helpers  ///////////////////////////////////////////////////////////////////////////////////////////

    protected operator fun <T> Stream<T>.invoke(handler: (T) -> Unit): () -> Unit {

        return this.subscribeToStream(handler).apply {
            unSubscribers.add(this)
        }
    }

    /**
     * Subscribes to the stream and publishes the streams value.
     *
     * When the stream publishes a new value a redraw is triggered
     */
    protected fun <T> stream(stream: Stream<T>, onNext: (T) -> Unit = {}): ReadOnlyProperty<Any?, T> {

        stream {
            onNext(it)
            triggerRedraw()
        }

        return object : ReadOnlyProperty<Any?, T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T {
                return stream()
            }
        }
    }

    /**
     * Creates a read write property for the components state
     */
    protected fun <T> property(initial: T, onChange: (T) -> Unit = {}): ReadWriteProperty<Any?, T> =
        object : ObservableProperty<T>(initial) {
            override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
                onChange(newValue)
                triggerRedraw()
            }
        }

    ////  Internal functions  //////////////////////////////////////////////////////////////////////////////////////////

    private fun _triggerRedrawRecursive() {
        needsRedraw = true

        // propagate the redraw to the parent
        if (parent != null) {
            parent._triggerRedrawRecursive()
        } else {
            ctx.engine.triggerRedraw()
        }
    }

    internal fun _nextCtx(newCtx: Ctx<PROPS>) {

        if (shouldRedraw(newCtx.props)) {
            needsRedraw = true
        }

        _props = newCtx.props
    }

    internal fun _internalOnRemove() {
        // unsubscribe from all stream subscriptions
        unSubscribers.forEach { it() }

        onRemove()
    }

    internal fun _internalRender(): Any? {
//        console.log("needsRedraw", needsRedraw, this)

        if (!needsRedraw) {
            return renderCache
        }

        needsRedraw = false

        @Suppress("UnsafeCastFromDynamic")
        renderCache = ctx.engine.render(this) { render() }

        return renderCache
    }
}

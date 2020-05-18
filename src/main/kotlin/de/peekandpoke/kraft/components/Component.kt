package de.peekandpoke.kraft.components

import de.peekandpoke.kraft.meiosis.Stream
import de.peekandpoke.kraft.vdom.VDom
import org.w3c.dom.Element
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Base class of all Components
 */
abstract class Component<PROPS, STATE>(val ctx: Ctx<PROPS>) {

    private val parent: Component<*, *>? = ctx.parent
    private var _props: PROPS = ctx.props

    private var needsRedraw = true
    private var renderCache: Any? = null

    private val unSubscribers = mutableListOf<() -> Unit>()

    val props: PROPS get() = _props

    var dom: Element? = null

    abstract val state: STATE

    abstract fun VDom.render()

    open fun onRemove() {
    }

    open fun shouldRedraw(nextProps: PROPS): Boolean {
//        console.log("nextProps", this, nextProps, props, nextProps != props)
        return props != nextProps
    }


    fun triggerRedraw() {
        // triggering a redraw for all parent components
        _triggerRedrawRecursive()
    }


    ////  Protected helpers  ///////////////////////////////////////////////////////////////////////////////////////////

    protected operator fun <T> Stream<T>.invoke(handler: (T) -> Unit): () -> Unit {

        return this.subscribeToStream(handler).apply {
            unSubscribers.add(this)
        }
    }

    protected fun <T> stream(stream: Stream<T>): ReadOnlyProperty<Any?, T> {

        stream { triggerRedraw() }

        return object : ReadOnlyProperty<Any?, T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T {
                return stream()
            }
        }
    }

    protected fun <T> property(initial: T): ReadWriteProperty<Any?, T> =
        object : ObservableProperty<T>(initial) {
            override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {
                console.log(property, oldValue, newValue)
                triggerRedraw()
            }
        }

    ////  Internal functions  //////////////////////////////////////////////////////////////////////////////////////////

    internal fun _triggerRedrawRecursive() {
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

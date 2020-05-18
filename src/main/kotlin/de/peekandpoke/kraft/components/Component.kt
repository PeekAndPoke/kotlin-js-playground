package de.peekandpoke.kraft.components

import de.peekandpoke.kraft.meiosis.Stream
import de.peekandpoke.kraft.vdom.VDom
import org.w3c.dom.Element

/**
 * Base class of all Components
 */
abstract class Component<PROPS, STATE>(val ctx: Ctx<PROPS>, initialState: STATE) {

    private val parent: Component<*, *>? = ctx.parent
    private var _props: PROPS = ctx.props
    private var _state: STATE = initialState

    private var needsRedraw = true
    private var renderCache: Any? = null

    private val unSubscribers = mutableListOf<() -> Unit>()

    val props: PROPS get() = _props
    val state: STATE get() = _state

    var dom: Element? = null

    abstract fun VDom.render()

    open fun onRemove() {
    }

    open fun shouldRedraw(nextProps: PROPS): Boolean {
//        console.log("nextProps", this, nextProps, props, nextProps != props)
        return props != nextProps
    }

    fun modState(mod: (STATE) -> STATE) {
        val newState = mod(_state)

        val shouldRedraw = newState != _state

        _state = newState

        if (shouldRedraw) {
            triggerRedraw()
        }
    }

    fun triggerRedraw() {
        // triggering a redraw for all parent components
        _triggerRedrawRecursive()
    }

    operator fun <T> Stream<T>.invoke(handler: (T) -> Unit): () -> Unit {

        return this.subscribeToStream(handler).apply {
            unSubscribers.add(this)
        }
    }

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

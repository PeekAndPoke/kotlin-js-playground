package de.peekandpoke.kraft.components

import de.peekandpoke.kraft.meiosis.Stream
import de.peekandpoke.kraft.vdom.VDom

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

    abstract fun VDom.render()

    open fun onRemove() {
    }

    fun modState(mod: (STATE) -> STATE) {
        val newState = mod(_state)

        if (newState != _state) {
            triggerRedraw()
        }

        _state = mod(_state)
    }

    fun triggerRedraw() {
        needsRedraw = true

        // propagate the redraw to the parent
        if (parent != null) {
            parent.triggerRedraw()
        } else {
            ctx.engine.triggerRedraw()
        }
    }

    operator fun <T> Stream<T>.invoke(handler: (T) -> Unit): () -> Unit {

        val handlerWithRedraw: (T) -> Unit = {
            triggerRedraw()
            handler(it)
        }

        return this.subscribeToStream(handlerWithRedraw).apply {
            unSubscribers.add(this)
        }
    }

    internal fun _nextCtx(newCtx: Ctx<PROPS>) {

        if (newCtx.props != _props) {
            triggerRedraw()
        }

        _props = newCtx.props
    }

    internal fun _internalOnRemove() {
        // unsubscribe from all stream subscriptions
        unSubscribers.forEach { it() }

        onRemove()
    }

    internal fun _internalRender(): Any? {

        if (!needsRedraw) {
            return renderCache
        }

        needsRedraw = false

        @Suppress("UnsafeCastFromDynamic")
        renderCache = ctx.engine.render(this) { render() }

        return renderCache
    }
}

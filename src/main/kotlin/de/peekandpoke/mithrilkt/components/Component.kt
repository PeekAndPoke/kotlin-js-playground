package de.peekandpoke.mithrilkt.components

import de.peekandpoke.mithrilkt.M

/**
 * Base class of all Components
 */
abstract class Component<PROPS, STATE>(ctx: Ctx<PROPS>, initialState: STATE) {

    private val parent: Component<*, *>? = ctx.parent
    private var _props: PROPS = ctx.props
    private var _state: STATE = initialState

    private var needsRedraw = true
    private var renderCache: Any? = null

    val props: PROPS get() = _props
    val state: STATE get() = _state

    abstract fun M.render()

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
        parent?.triggerRedraw()
    }

    internal fun _nextCtx(newCtx: Ctx<PROPS>) {

        if (newCtx.props != _props) {
            triggerRedraw()
        }

        _props = newCtx.props
    }

    internal fun _internalRender(): Any? {

        if (!needsRedraw) {
            return renderCache
        }

        needsRedraw = false

        @Suppress("UnsafeCastFromDynamic")
        renderCache = M(this).render { render() }

        return renderCache
    }
}

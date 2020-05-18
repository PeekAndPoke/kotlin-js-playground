package de.peekandpoke.kraft.components

/**
 * Base component that has props but no state
 */
abstract class StatelessComponent<PROPS>(ctx: Ctx<PROPS>) : Component<PROPS, Nothing?>(ctx) {
    override val state = null
}

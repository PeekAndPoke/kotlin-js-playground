package de.peekandpoke.kraft.components

/**
 * Base component that has a state but no props
 */
abstract class ProplessComponent<STATE>(ctx: NoProps) : Component<Nothing?, STATE>(ctx)

package de.peekandpoke.kraft.components

/**
 * A static component does not have any props or state
 */
abstract class StaticComponent(ctx: Ctx<Nothing?>) : Component<Nothing?, Nothing?>(ctx, null)

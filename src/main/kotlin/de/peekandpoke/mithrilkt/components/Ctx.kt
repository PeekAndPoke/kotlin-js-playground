package de.peekandpoke.mithrilkt.components

data class Ctx<PROPS>(
    val parent: Component<*, *>?,
    val props: PROPS
) {
    companion object {
        val Null = Ctx(null, null)
    }
}

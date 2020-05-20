package de.peekandpoke.app.utils

fun <T> List<T>.modifyAt(idx: Int, modifier: (T) -> T): List<T> {
    val mutable = toMutableList()

    mutable[idx] = modifier(mutable[idx])

    return mutable.toList()
}

fun <T> List<T>.removeAt(idx: Int): List<T> {
    return toMutableList().apply { removeAt(idx) }.toList()
}

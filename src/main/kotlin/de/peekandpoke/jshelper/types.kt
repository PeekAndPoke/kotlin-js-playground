@file:Suppress("UnsafeCastFromDynamic")

package de.peekandpoke.jshelper

fun jsIsObject(o: dynamic): Boolean = jsTypeOf(o) == "object"

fun jsIsArray(o: dynamic): Boolean = js("(Array.isArray(o))") as Boolean

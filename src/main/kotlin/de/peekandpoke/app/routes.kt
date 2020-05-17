package de.peekandpoke.app

import de.peekandpoke.kraft.routing.StaticRoute

object Nav {
    val home = StaticRoute("")

    val counters = StaticRoute("/counters")

    val remote = StaticRoute("/remote")
}


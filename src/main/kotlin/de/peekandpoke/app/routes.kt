package de.peekandpoke.app

import de.peekandpoke.kraft.routing.ParameterizedRoute1
import de.peekandpoke.kraft.routing.StaticRoute

object Nav {

    val login = StaticRoute("/login")

    val home = StaticRoute("")

    val counters = StaticRoute("/counters")

    val remote = StaticRoute("/remote")

    val orgs = ParameterizedRoute1("/org/{id}")
}


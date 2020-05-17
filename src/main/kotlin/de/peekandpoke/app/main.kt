package de.peekandpoke.app

import de.peekandpoke.kraft.routing.Router

val router = Router(listOf(
    Nav.home,
    Nav.counters,
    Nav.remote
))

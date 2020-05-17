package de.peekandpoke.app

import de.peekandpoke.kraft.routing.ParameterizedRoute1
import de.peekandpoke.kraft.routing.Router
import kotlin.browser.window

val router = Router(listOf(
    Nav.login,
    Nav.home,
    Nav.counters,
    Nav.remote
))

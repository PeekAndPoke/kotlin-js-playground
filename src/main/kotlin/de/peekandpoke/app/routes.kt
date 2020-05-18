package de.peekandpoke.app

import de.peekandpoke.kraft.routing.ParameterizedRoute1
import de.peekandpoke.kraft.routing.StaticRoute
import de.peekandpoke.kraft.routing.router
import de.peekandpoke.kraft.routing.routerMiddleware

object Nav {
    val login = StaticRoute("/login")
    val home = StaticRoute("")
    val counters = StaticRoute("/counters")
    val remote = StaticRoute("/remote")
    val orgs = ParameterizedRoute1("/org/{id}")

    val adminUsersList = StaticRoute("/adminusers")
}

val isLoggedIn = routerMiddleware {
    if (AppState.user() == null) {
        router.navTo(Nav.login())
    }
}

val router = router {

    mount(Nav.login)

    using(isLoggedIn) {
        mount(
            Nav.login,
            Nav.home,
            Nav.counters,
            Nav.remote,
            Nav.orgs,

            Nav.adminUsersList
        )
    }
}

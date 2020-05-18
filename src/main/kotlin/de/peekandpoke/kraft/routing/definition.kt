package de.peekandpoke.kraft.routing

interface Route {

    val pattern: String

    fun match(uri: String): MatchedRoute?

    companion object {
        val default = StaticRoute("")
    }

    fun String.replacePlaceholder(placeholder: String, value: String) =
        replace("{$placeholder}", value)
}

data class MatchedRoute(
    val route: Route,
    val params: Map<String, String>
) {
    companion object {
        val default = MatchedRoute(route = Route.default, params = emptyMap())
    }

    val pattern = route.pattern

    fun param(name: String, default: String = ""): String = params[name] ?: default
}

data class StaticRoute(override val pattern: String) : Route {
    override fun match(uri: String) = when (this.pattern.trim() == uri.trim()) {
        true -> MatchedRoute(route = this, params = emptyMap())
        else -> null
    }

    operator fun invoke() = "#$pattern"
}

data class ParameterizedRoute1(override val pattern: String) : Route {

    companion object {
        val placeholderRegex = "\\{([^}]*)}".toRegex()
        val extractRegexPattern = "([^/]*)"
    }

    private val placeholders = placeholderRegex
        .findAll(pattern)
        .map { it.groupValues[1] }
        // remove the special ... suffix on ktor routes
        .map { it.removeSuffix("...") }
        .toList()

    private val matchingRegex = placeholders.fold(pattern) { acc, placeholder ->
        acc.replace("{$placeholder}", extractRegexPattern)
    }.replace("/", "\\/").toRegex()

    override fun match(uri: String): MatchedRoute? {

//        js("debugger")

        val match = matchingRegex.matchEntire(uri) ?: return null

//        console.log(placeholders)
//        console.log(match)
//        console.log(match.groupValues)

        return MatchedRoute(
            route = this,
            params = placeholders.zip(match.groupValues).toMap()
        )
    }

    operator fun invoke(p1: String) = "#$pattern".replacePlaceholder(placeholders[0], p1)
}




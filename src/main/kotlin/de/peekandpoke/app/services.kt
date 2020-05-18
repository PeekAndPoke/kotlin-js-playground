package de.peekandpoke.app

import de.peekandpoke.app.api.AdminUsersClient
import de.peekandpoke.app.api.LoginClient
import de.peekandpoke.app.domain.domainCodec

// TODO: make this configurable
const val baseUrl = "http://api.jointhebase.local:8080"

object Api {
    val login = LoginClient(baseUrl, domainCodec)

    val adminUsers = AdminUsersClient(baseUrl, domainCodec, AppState.authToken)
}

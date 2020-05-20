package de.peekandpoke.app

import de.peekandpoke.app.api.AdminUsersApiApiClient
import de.peekandpoke.app.api.CmsApiClient
import de.peekandpoke.app.api.LoginApiClient
import de.peekandpoke.app.api.OrganisationsApiClient
import de.peekandpoke.app.domain.domainCodec

// TODO: make this configurable
const val baseUrl = "http://api.jointhebase.local:8080"

object Api {
    val login = LoginApiClient(baseUrl, domainCodec)

    val adminUsers = AdminUsersApiApiClient(baseUrl, AppState.authToken, domainCodec)

    val cms = CmsApiClient(baseUrl, AppState.authToken, domainCodec)

    val organisations = OrganisationsApiClient(baseUrl, AppState.authToken, domainCodec)
}

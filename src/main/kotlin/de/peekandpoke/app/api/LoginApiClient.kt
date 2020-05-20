package de.peekandpoke.app.api

import de.peekandpoke.app.domain.login.LoginResponse
import de.peekandpoke.kraft.remote.body
import de.peekandpoke.kraft.remote.onErrorLog
import de.peekandpoke.kraft.remote.remote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json


class LoginApiClient(private val baseUrl: String, private val codec: Json) {

    private val remote get() = remote("$baseUrl/adminusers")

    fun login(user: String, password: String): Flow<ApiResponse<LoginResponse>> =
        remote
            .post(
                url = "login",
                body = codec.stringify(
                    ApiQueries.LoginWithPassword.serializer(),
                    ApiQueries.LoginWithPassword(user, password)
                )
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(LoginResponse.serializer()), it) }

}

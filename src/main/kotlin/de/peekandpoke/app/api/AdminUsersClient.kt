package de.peekandpoke.app.api

import de.peekandpoke.app.domain.adminusers.AdminUserModel
import de.peekandpoke.kraft.meiosis.Stream
import de.peekandpoke.kraft.remote.body
import de.peekandpoke.kraft.remote.onErrorLog
import de.peekandpoke.kraft.remote.remote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json


class AdminUsersClient(
    private val baseUrl: String,
    private val codec: Json,
    private val token: Stream<String?>
) {

    private val remote get() = remote(baseUrl).header("Authorization", "Bearer ${token()}")

    fun list(search: String = ""): Flow<ApiResponse<List<AdminUserModel>>> =
        remote.get(url = "adminusers")
            .onErrorLog()
            .body()
            .map {
                codec.parse(ApiResponse.serializer(AdminUserModel.serializer().list), it)
            }
}

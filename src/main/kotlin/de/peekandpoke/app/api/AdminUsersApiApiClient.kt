package de.peekandpoke.app.api

import de.peekandpoke.app.domain.adminusers.AdminUserModel
import de.peekandpoke.app.domain.adminusers.AdminUserProfile
import de.peekandpoke.kraft.store.Stream
import de.peekandpoke.kraft.remote.body
import de.peekandpoke.kraft.remote.onErrorLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json

class AdminUsersApiApiClient(
    baseUrl: String,
    token: Stream<String?>,
    private val codec: Json
) : AuthorizedApiClientBase(baseUrl, token) {

    fun list(search: String = ""): Flow<ApiResponse<List<AdminUserModel>>> =
        remote
            .get(url = uri("adminusers", mapOf("search" to search)))
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(AdminUserModel.serializer().list), it) }

    fun save(orgId: String, userId: String, body: AdminUserModel): Flow<ApiResponse<AdminUserModel>> =
        remote
            .put(
                url = uri("adminusers/${orgId.enc()}/${userId.enc()}"),
                body = codec.stringify(AdminUserModel.serializer(), body)
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(AdminUserModel.serializer()), it) }

    fun saveProfile(orgId: String, userId: String, body: AdminUserProfile): Flow<ApiResponse<AdminUserModel>> =
        remote
            .put(
                url = uri("adminusers/${orgId.enc()}/${userId.enc()}/profile"),
                body = codec.stringify(AdminUserProfile.serializer(), body)
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(AdminUserModel.serializer()), it) }

    fun getInOrg(orgId: String, userId: String): Flow<ApiResponse<AdminUserModel>> =
        remote
            .get(uri("adminusers/${orgId.enc()}/${userId.enc()}"))
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(AdminUserModel.serializer()), it) }

    fun listInOrg(orgId: String, search: String = ""): Flow<ApiResponse<List<AdminUserModel>>> =
        remote
            .get(uri("adminusers/${orgId.enc()}", mapOf("search" to search)))
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(AdminUserModel.serializer().list), it) }
}

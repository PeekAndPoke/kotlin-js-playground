package de.peekandpoke.app.api

import de.peekandpoke.app.domain.domainCodec
import de.peekandpoke.app.domain.organisations.OrganisationModel
import de.peekandpoke.kraft.store.Stream
import de.peekandpoke.kraft.remote.body
import de.peekandpoke.kraft.remote.onErrorLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json


class OrganisationsClient(
    baseUrl: String,
    token: Stream<String?>,
    private val codec: Json
) : AuthorizedClientBase(baseUrl, token) {

    fun search(search: String = ""): Flow<ApiResponse<List<OrganisationModel>>> =
        remote
            .get(
                url = uri("/organisations", mapOf("search" to search))
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(OrganisationModel.serializer().list), it) }

    fun get(id: String): Flow<ApiResponse<OrganisationModel>> =
        remote
            .get(
                url = uri("/organisations/${id.enc()}")
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(OrganisationModel.serializer()), it) }

    fun save(id: String, body: OrganisationModel): Flow<ApiResponse<OrganisationModel>> =
        remote
            .put(
                url = uri("/organisations/${id.enc()}"),
                body = domainCodec.stringify(OrganisationModel.serializer(), body)
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(OrganisationModel.serializer()), it) }
}

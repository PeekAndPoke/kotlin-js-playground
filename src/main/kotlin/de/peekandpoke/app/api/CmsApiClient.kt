package de.peekandpoke.app.api

import de.peekandpoke.app.domain.cms.CmsPageModel
import de.peekandpoke.app.domain.domainCodec
import de.peekandpoke.app.domain.organisations.OrganisationModel
import de.peekandpoke.kraft.remote.body
import de.peekandpoke.kraft.remote.onErrorLog
import de.peekandpoke.kraft.store.Stream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json

class CmsApiClient(
    baseUrl: String,
    token: Stream<String?>,
    private val codec: Json
) : AuthorizedApiClientBase(baseUrl, token) {

    fun searchPages(search: String = ""): Flow<ApiResponse<List<CmsPageModel>>> =
        remote
            .get(
                url = uri("/cms/pages", mapOf("search" to search))
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(CmsPageModel.serializer().list), it) }

    fun getPage(id: String): Flow<ApiResponse<CmsPageModel>> =
        remote
            .get(
                url = uri("/cms/pages/${id.enc()}")
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(CmsPageModel.serializer()), it) }


    fun savePage(id: String, body: CmsPageModel): Flow<ApiResponse<CmsPageModel>> =
        remote
            .put(
                url = uri("/cms/pages"),
                body = domainCodec.stringify(CmsPageModel.serializer(), body)
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(CmsPageModel.serializer()), it) }
}

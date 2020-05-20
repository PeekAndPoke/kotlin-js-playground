package de.peekandpoke.app.api

import de.peekandpoke.app.domain.cms.CmsMenuModel
import de.peekandpoke.app.domain.cms.CmsPageModel
import de.peekandpoke.app.domain.cms.CmsSnippetModel
import de.peekandpoke.app.domain.domainCodec
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

    ////  PAGES  ///////////////////////////////////////////////////////////////////////////////////////////////////////

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

    ////  MENUS  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    fun searchMenus(search: String = ""): Flow<ApiResponse<List<CmsMenuModel>>> =
        remote
            .get(
                url = uri("/cms/menus", mapOf("search" to search))
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(CmsMenuModel.serializer().list), it) }

    fun getMenu(id: String): Flow<ApiResponse<CmsMenuModel>> =
        remote
            .get(
                url = uri("/cms/menus/${id.enc()}")
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(CmsMenuModel.serializer()), it) }


    fun saveMenu(id: String, body: CmsMenuModel): Flow<ApiResponse<CmsMenuModel>> =
        remote
            .put(
                url = uri("/cms/menus"),
                body = domainCodec.stringify(CmsMenuModel.serializer(), body)
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(CmsMenuModel.serializer()), it) }

    ////  SNIPPETS  ////////////////////////////////////////////////////////////////////////////////////////////////////

    fun searchSnippets(search: String = ""): Flow<ApiResponse<List<CmsSnippetModel>>> =
        remote
            .get(
                url = uri("/cms/snippets", mapOf("search" to search))
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(CmsSnippetModel.serializer().list), it) }

    fun getSnippet(id: String): Flow<ApiResponse<CmsSnippetModel>> =
        remote
            .get(
                url = uri("/cms/snippets/${id.enc()}")
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(CmsSnippetModel.serializer()), it) }


    fun saveSnippet(id: String, body: CmsSnippetModel): Flow<ApiResponse<CmsSnippetModel>> =
        remote
            .put(
                url = uri("/cms/snippets"),
                body = domainCodec.stringify(CmsSnippetModel.serializer(), body)
            )
            .onErrorLog()
            .body()
            .map { codec.parse(ApiResponse.serializer(CmsSnippetModel.serializer()), it) }
}

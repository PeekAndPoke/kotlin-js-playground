package de.peekandpoke.app.domain.cms

import de.peekandpoke.app.domain.cms.layouts.CmsLayout
import kotlinx.serialization.Serializable

@Serializable
data class CmsPageModel(
    val id: String,
    val name: String,
    val uri: String,
    val tags: String,
    val meta: Meta,
    val layout: CmsLayout
//    val storableMeta: StorableMeta?
) {
    @Serializable
    data class Meta(
        val title: String = "",
        val description: String = "",
        val robotsIndex: Boolean = true,
        val robotsFollow: Boolean = true,
        val alternateLanguages: List<AlternateLanguage> = listOf()
    ) {
        val robots: String = listOf(
            if (robotsIndex) "index" else "noindex",
            if (robotsFollow) "follow" else "nofollow"
        ).joinToString(", ")
    }

    @Serializable
    data class AlternateLanguage(
        val language: String = "",
        val url: LinkUrl = LinkUrl()
    )
}

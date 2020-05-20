package de.peekandpoke.app.domain.cms

import kotlinx.serialization.Serializable

@Serializable
data class CmsMenuModel(
    val id: String,
    val name: String,
    val entries: List<Entry>
//    val storableMeta: StorableMeta?
) {
    @Serializable
    data class Entry(
        val link: Link = Link(),
        val subs: List<Link> = listOf()
    ) {
        fun hasSubs() = subs.isNotEmpty()
    }
}

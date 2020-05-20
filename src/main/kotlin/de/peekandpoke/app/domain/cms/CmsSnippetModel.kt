package de.peekandpoke.app.domain.cms

import de.peekandpoke.app.domain.cms.elements.CmsElement
import kotlinx.serialization.Serializable

@Serializable
data class CmsSnippetModel(
    val id: String,
    val name: String,
    val tags: String,
    val element: CmsElement
//    val storableMeta: StorableMeta?
)

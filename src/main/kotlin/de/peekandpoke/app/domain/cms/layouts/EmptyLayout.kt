package de.peekandpoke.app.domain.cms.layouts

import de.peekandpoke.app.domain.cms.elements.CmsElement
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("empty-layout")
data class EmptyLayout(
    val elements: List<CmsElement> = listOf()
) : CmsLayout()

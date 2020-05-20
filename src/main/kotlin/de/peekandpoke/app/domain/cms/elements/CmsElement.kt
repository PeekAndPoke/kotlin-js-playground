package de.peekandpoke.app.domain.cms.elements

import kotlinx.serialization.Serializable

@Serializable
abstract class CmsElement {
    abstract val elementName: String

    abstract val elementDescription: String
}

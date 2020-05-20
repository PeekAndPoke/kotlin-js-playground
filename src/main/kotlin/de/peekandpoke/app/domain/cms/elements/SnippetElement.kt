package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.ElementStyle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("snippet-element")
data class SnippetElement(
    val name: String = "",
    /** The id of the referenced snippet */
    val snippet: String? = null,
    val overrideStyle: Boolean = false,
    val overriddenStyle: ElementStyle = ElementStyle.default
) : CmsElement() {

    /** Element name */
    override val elementName get() = "Snippet"

    /** Element description */
    override val elementDescription get() = "$elementName '${name}'"
}

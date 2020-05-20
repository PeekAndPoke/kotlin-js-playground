package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.ElementStyle
import de.peekandpoke.app.domain.cms.Markdown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("footer-element")
data class FooterElement(
    val styling: ElementStyle = ElementStyle.default,
    val headline: String = "",
    val middle: Markdown = Markdown(),
    val right: String = "",
    /** The id of the references CmsMenu */
    val menu: String? = null
) : CmsElement() {

    override val elementName get() = "Footer"

    override val elementDescription get() = "$elementName '$headline'"
}

package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.CallToAction
import de.peekandpoke.app.domain.cms.ElementStyle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("header-element")
data class HeaderElement(
    val styling: ElementStyle = ElementStyle.default,
    /** The id of the references CmsMenu */
    val menu: String? = null,
    val cta: CallToAction = CallToAction()
) : CmsElement() {

    override val elementName get() = "Header"

    override val elementDescription get() = "$elementName - Cta:${cta.text}"
}

package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.CallToAction
import de.peekandpoke.app.domain.cms.ElementPadding
import de.peekandpoke.app.domain.cms.ElementStyle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("cta-element")
data class CallToActionElement(
    val styling: ElementStyle = ElementStyle.default,
    val padding: ElementPadding = ElementPadding.default,
    val cta: CallToAction = CallToAction()
) : CmsElement() {

    override val elementName get() = "Call-To-Action"

    override val elementDescription get() = "$elementName '${cta.text}'"
}

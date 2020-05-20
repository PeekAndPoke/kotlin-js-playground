package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.CallToAction
import de.peekandpoke.app.domain.cms.ElementPadding
import de.peekandpoke.app.domain.cms.ElementStyle
import de.peekandpoke.app.domain.cms.Markdown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("text-element")
data class TextElement(
    val styling: ElementStyle = ElementStyle.default,
    val padding: ElementPadding = ElementPadding.default,
    val headline: String = "",
    val text: Markdown = Markdown.default,
    val cta: CallToAction = CallToAction.default
): CmsElement() {

    override val elementName get() = "Text"

    override val elementDescription get() = "$elementName '$headline' '${text.content.take(20)}'"
}

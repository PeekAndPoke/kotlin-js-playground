package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.CallToAction
import de.peekandpoke.app.domain.cms.ElementStyle
import de.peekandpoke.app.domain.cms.Image
import de.peekandpoke.app.domain.cms.Markdown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("hero-element")
data class HeroElement(
    val styling: ElementStyle = ElementStyle.default,
    val layout: Layout = Layout.ImageRight,
    val headline: String = "",
    val text: Markdown = Markdown.default,
    val pattern: String = "hero001",
    val cta: CallToAction = CallToAction.default,
    val images: List<Image> = emptyList()
) : CmsElement() {

    enum class Layout {
        ImageRight,
        FullSizeImage
    }

    /** Element name */
    override val elementName get() = "Hero"

    /** Element description */
    override val elementDescription get() = "$elementName '$headline' '${text.content.take(20)}'"
}

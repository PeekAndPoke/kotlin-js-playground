package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.ElementPadding
import de.peekandpoke.app.domain.cms.ElementStyle
import de.peekandpoke.app.domain.cms.Image
import de.peekandpoke.app.domain.cms.Markdown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("text-image-element")
data class TextImageElement(
    val styling: ElementStyle = ElementStyle.default,
    val padding: ElementPadding = ElementPadding.default,
    val layout: Layout = Layout.ImageLeft,
    val headline: String = "",
    val text: Markdown = Markdown(),
    val images: List<Image> = listOf()
) : CmsElement() {

    /** Element name */
    override val elementName get() = "Text & Image"

    /** Element description */
    override val elementDescription get() = "$elementName '$headline'"

    /**
     * Layouts for the element
     */
    enum class Layout {
        ImageLeft,
        ImageRight,
        ImageTop,
        ImageBottom
    }
}

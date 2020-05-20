package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.ElementPadding
import de.peekandpoke.app.domain.cms.ElementStyle
import de.peekandpoke.app.domain.cms.Image
import de.peekandpoke.app.domain.cms.Markdown
import de.peekandpoke.ultrajs.semanticui.SemanticColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("list-and-image-element")
data class ListAndImageElement(
    val styling: ElementStyle = ElementStyle.default,
    val padding: ElementPadding = ElementPadding.default,
    val layout: Layout = Layout.ImageLeft,
    val headline: String = "",
    val image: Image = Image(),
    val items: List<Item> = listOf()
) : CmsElement() {

    /** Element name */
    override val elementName get() = "List & Image"

    /** Element description */
    override val elementDescription get() = "$elementName '$headline'"

    /**
     * Layouts for the element
     */
    enum class Layout {
        ImageLeft,
        ImageRight
    }

    @Serializable
    data class Item(
        val icon: String = "",
        val iconColor: SemanticColor = SemanticColor.default,
        val text: Markdown = Markdown()
    )
}

package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.CallToAction
import de.peekandpoke.app.domain.cms.ElementStyle
import de.peekandpoke.app.domain.cms.Markdown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("list-with-ctas-element")
data class ListWithCtasElement(
    val styling: ElementStyle = ElementStyle.default,
    val layout: Layout = Layout.Default,
    val headline: String = "",
    val text: Markdown = Markdown(),
    val items: List<Item> = listOf()
) : CmsElement() {

    /** Element name */
    override val elementName get() = "List with CTAs"

    /** Element description */
    override val elementDescription get() = "$elementName '$headline'"

    /**
     * Layouts for the element
     */
    enum class Layout {
        Default,
        HorizontalDividers,
        HorizontalDividersTopBottom
    }

    /**
     * Entry of the list
     */
    @Serializable
    data class Item(
        val text: Markdown = Markdown(),
        val cta: CallToAction = CallToAction()
    )
}

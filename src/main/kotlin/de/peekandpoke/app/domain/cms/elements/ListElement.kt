package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.CallToAction
import de.peekandpoke.app.domain.cms.ElementStyle
import de.peekandpoke.app.domain.cms.Markdown
import de.peekandpoke.ultrajs.semanticui.SemanticColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("list-element")
data class ListElement(
    val styling: ElementStyle = ElementStyle.default,
    val layout: Layout = Layout.Default,
    val headline: String = "",
    val text: Markdown = Markdown(),
    val cta: CallToAction = CallToAction(),
    val subHead1: String = "",
    val items1: List<Item> = listOf(),
    val subHead2: String = "",
    val items2: List<Item> = listOf(),
    val subHead3: String = "",
    val items3: List<Item> = listOf()
) : CmsElement() {

    /** Element name */
    override val elementName get() = "List"

    /** Element description */
    override val elementDescription get() = "$elementName '$headline'"

    /**
     * Layouts for the list element
     */
    enum class Layout {
        Default,
        HorizontalDividers
    }

    /**
     * Entry item for the list
     */
    @Serializable
    data class Item(
        val icon: String = "",
        val iconColor: SemanticColor = SemanticColor.default,
        val text: String = ""
    )
}

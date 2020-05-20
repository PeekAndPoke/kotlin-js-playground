package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.ElementPadding
import de.peekandpoke.app.domain.cms.ElementStyle
import de.peekandpoke.app.domain.cms.Markdown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("facts-and-figures-element")
data class FactsAndFiguresElement(
    val styling: ElementStyle = ElementStyle.default,
    val padding: ElementPadding = ElementPadding.default,
    val headline: String = "",
    val text: Markdown = Markdown(),
    val items: List<Item> = listOf()
) : CmsElement() {

    override val elementName get() = "Facts & Figures"

    override val elementDescription get() = "$elementName '$headline'"

    @Serializable
    data class Item(
        val headline: String = "",
        val subHeadline: String = "",
        val text: String = ""
    )
}

package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("gallery-element")
data class GalleryElement(
    val styling: ElementStyle = ElementStyle.default,
    val padding: ElementPadding = ElementPadding.default,
    val layout: Layout = Layout.SideBySideSlider,
    val headline: String = "",
    val text: Markdown = Markdown(),
    val cta: CallToAction = CallToAction(),
    val items: List<Item> = listOf()
) : CmsElement() {

    override val elementName get() = "Gallery"

    override val elementDescription get() = "$elementName '$headline' - '$layout'"

    @Serializable
    data class Item(
        val headline: String = "",
        val text: Markdown = Markdown(),
        val image: Image = Image()
    )

    enum class Layout {
        SideBySideSlider,
        IconSlider,
        FullWidthSlider,
        ThreeColumns,
        FiveColumns,
        ThreeColumnsIcons
    }
}

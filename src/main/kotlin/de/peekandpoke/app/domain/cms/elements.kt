package de.peekandpoke.app.domain.cms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule

val cmsModule = SerializersModule {
    polymorphic(CmsElement::class) {
        HeroElement::class with HeroElement.serializer()
        TextElement::class with TextElement.serializer()
    }
}

@Serializable
abstract class CmsElement

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
}

@Serializable
@SerialName("text-element")
data class TextElement(
    val styling: ElementStyle = ElementStyle.default,
    val padding: ElementPadding = ElementPadding.default,
    val headline: String = "",
    val text: Markdown = Markdown.default,
    val cta: CallToAction = CallToAction.default
): CmsElement()

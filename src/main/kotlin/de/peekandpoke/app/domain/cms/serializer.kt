package de.peekandpoke.app.domain.cms

import de.peekandpoke.app.domain.cms.elements.*
import de.peekandpoke.app.domain.cms.layouts.CmsLayout
import de.peekandpoke.app.domain.cms.layouts.EmptyLayout
import de.peekandpoke.app.domain.cms.layouts.LandingPageLayout
import kotlinx.serialization.modules.SerializersModule

val cmsModule = SerializersModule {

    polymorphic(CmsLayout::class) {
        EmptyLayout::class with EmptyLayout.serializer()

        LandingPageLayout::class with LandingPageLayout.serializer()
    }

    polymorphic(CmsElement::class) {

        CallToActionElement::class with CallToActionElement.serializer()

        DividerElement::class with DividerElement.serializer()

        FactsAndFiguresElement::class with FactsAndFiguresElement.serializer()

        FooterElement::class with FooterElement.serializer()

        GalleryElement::class with GalleryElement.serializer()

        HeaderElement::class with HeaderElement.serializer()

        HeroElement::class with HeroElement.serializer()

        ListAndImageElement::class with ListAndImageElement.serializer()

        ListElement::class with ListElement.serializer()

        ListWithCtasElement::class with ListWithCtasElement.serializer()

        PartnerContactFormElement::class with PartnerContactFormElement.serializer()

        SnippetElement::class with SnippetElement.serializer()

        TextElement::class with TextElement.serializer()

        TextImageElement::class with TextImageElement.serializer()
    }
}


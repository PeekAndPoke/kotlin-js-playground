package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.app.domain.cms.ElementStyle
import de.peekandpoke.app.domain.cms.Image
import de.peekandpoke.app.domain.cms.Markdown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("partner-contact-form-element")
data class PartnerContactFormElement(
    val styling: ElementStyle = ElementStyle.default,
    val headline: String = "",
    val image: Image = Image(),
    val text: Markdown = Markdown(),
    val placeholders: PlaceHolders = PlaceHolders(),
    val successText: Markdown = Markdown(),
    val errorText: Markdown = Markdown()
) : CmsElement() {

    @Serializable
    data class PlaceHolders(
        val forName: String = "",
        val forEmail: String = "",
        val forPhoneCountry: String = "",
        val forPhoneNumber: String = "",
        val forMessage: String = ""
    )

    /** Element name */
    override val elementName get() = "Partner Contact Form"

    /** Element description */
    override val elementDescription get() = "$elementName '${headline}'"
}

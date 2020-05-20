package de.peekandpoke.app.domain.cms.elements

import de.peekandpoke.ultrajs.semanticui.SemanticColor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("divider-element")
data class DividerElement(
    val background: SemanticColor = SemanticColor.default,
    val height: Height = Height.one,
    val pattern: String = "divider001"
) : CmsElement() {

    override val elementName get() = "Divider"

    override val elementDescription get() = "$elementName height:$height pattern:$pattern"

    @Suppress("EnumEntryName")
    enum class Height {
        one,
        two,
        three,
        four,
        five,
        six
    }
}

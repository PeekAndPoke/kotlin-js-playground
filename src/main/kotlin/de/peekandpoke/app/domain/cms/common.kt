package de.peekandpoke.app.domain.cms

import de.peekandpoke.ultrajs.semanticui.SemanticColor
import kotlinx.serialization.Serializable

@Serializable
data class Link(
    val title: String = "",
    val url: LinkUrl = LinkUrl("")
)

@Serializable
data class LinkUrl(val url: String = "")

@Serializable
data class CallToAction(
    val text: String = "",
    val icon: String = "",
    val color: SemanticColor = SemanticColor.default,
    val inverted: Boolean = false,
    val url: LinkUrl = LinkUrl()
) {
    companion object {
        val default = CallToAction()
    }
}

@Serializable
data class ElementPadding(
    val paddingTop: Boolean = true,
    val paddingBottom: Boolean = true
) {
    companion object {
        val default = ElementPadding()
    }
}

@Serializable
data class ElementStyle(
    val backgroundColor: SemanticColor = SemanticColor.default,
    val textColor: SemanticColor = SemanticColor.default
) {
    companion object {
        val default = ElementStyle()
    }
}

@Serializable
data class Image(
    val url: String = "",
    val alt: String = ""
)

@Serializable
data class Markdown(val content: String = "") {

    companion object {
        val default = Markdown()
    }

    fun isNotBlank() = content.isNotBlank()
}

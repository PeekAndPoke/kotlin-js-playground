package de.peekandpoke.kraft.styles

import kotlinx.css.CSSBuilder
import kotlinx.html.Tag

fun Tag.css(builder: CSSBuilder.() -> Unit) {
    attributes["style"] = CSSBuilder().apply(builder).toString()
}

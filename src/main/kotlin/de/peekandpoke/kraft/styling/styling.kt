package de.peekandpoke.kraft.styling

import kotlinx.css.CSSBuilder
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.style

data class InlineStyle(val css: String)

fun inlineStyle(block: CSSBuilder.() -> Unit): InlineStyle = InlineStyle(CSSBuilder().apply(block).toString())

fun CommonAttributeGroupFacade.css(block: CSSBuilder.() -> Unit) {
    style = CSSBuilder().apply(block).toString()
}

fun CommonAttributeGroupFacade.css(css: InlineStyle) {
    style = css.css
}


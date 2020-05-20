package de.peekandpoke.app.ui

import de.peekandpoke.kraft.styling.inlineStyle
import kotlinx.css.*

object Theme {

    object Colors {
        val dark = Color("#1e2129")
        val lightDark = Color("#2d323e")
    }

    object Pages {
        val whiteText = inlineStyle {
            color = Color.white
        }

        val headerPadding = inlineStyle {
            paddingTop = 50.px
            paddingBottom = 50.px
        }
    }
}

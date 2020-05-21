package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.ui.components.forms.ListFieldComponent
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.title

fun <T> FlowContent.leftRemoveRightButtons(ctx: ListFieldComponent.ItemContext<T>) {

    if (ctx.idx > 0) {
        ui.icon.button {
            title = "Move Left"
            icon.arrow_left()
            onClick { ctx.swapWith(ctx.idx - 1) }
        }
    }

    ui.icon.button {
        title = "Remove"
        icon.close()
        onClick { ctx.remove() }
    }

    if (ctx.idx < ctx.all.size - 1) {
        ui.icon.button {
            title = "Move Right"
            icon.arrow_right()
            onClick { ctx.swapWith(ctx.idx + 1) }
        }
    }
}

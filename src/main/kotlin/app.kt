import de.peekandpoke.app.container
import de.peekandpoke.mithrilkt.components.RootComponent
import kotlin.browser.document

fun main() {

    document.addEventListener("DOMContentLoaded", {
        RootComponent { container() }.mountAt(document.body!!)
    })
}


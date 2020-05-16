import de.peekandpoke.app.container
import de.peekandpoke.kraft.vdom.mithril.MithrilVDomEngine
import kotlin.browser.document

fun main() {

    document.addEventListener("DOMContentLoaded", {

        MithrilVDomEngine().mount(document.body!!) {
            container()
        }
    })
}


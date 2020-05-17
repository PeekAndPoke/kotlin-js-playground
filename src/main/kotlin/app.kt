import de.peekandpoke.app.container
import de.peekandpoke.app.router
import de.peekandpoke.kraft.vdom.mithril.MithrilVDomEngine
import org.w3c.dom.HTMLElement
import kotlin.browser.document

fun main() {


    document.addEventListener("DOMContentLoaded", {

        val mount = document.getElementById("spa") as HTMLElement

        MithrilVDomEngine().mount(mount) {
            container()
        }
    })
}


import de.peekandpoke.app.app
import de.peekandpoke.kraft.vdom.mithril.MithrilVDomEngine
import org.w3c.dom.HTMLElement
import kotlin.browser.document

fun main() {

    document.addEventListener("DOMContentLoaded", {

        val mountPoint = document.getElementById("spa") as HTMLElement

        MithrilVDomEngine().mount(mountPoint) { app() }
    })
}

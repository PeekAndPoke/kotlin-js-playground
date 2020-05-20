import de.peekandpoke.app.app
import de.peekandpoke.app.domain.adminusers.AdminUserModel
import de.peekandpoke.app.domain.cms.CmsElement
import de.peekandpoke.app.domain.cms.HeroElement
import de.peekandpoke.app.domain.cms.TextElement
import de.peekandpoke.app.domain.domainCodec
import de.peekandpoke.jshelper.js
import de.peekandpoke.kraft.vdom.mithril.MithrilVDomEngine
import kotlinx.serialization.builtins.list
import org.w3c.dom.HTMLElement
import kotlin.browser.document

fun main() {

    testCmsElements()

    document.addEventListener("DOMContentLoaded", {

        val mount = document.getElementById("spa") as HTMLElement

        MithrilVDomEngine().mount(mount) { app() }
    })
}

private fun testCmsElements() {

    val elements = listOf(
        HeroElement(),
        TextElement()
    )

    val serialized = domainCodec.stringify(
        CmsElement.serializer().list,
        elements
    )

    console.log("cms elements serialized", serialized)

    val deserialized = domainCodec.parse(
        CmsElement.serializer().list,
        serialized
    )

    console.log("cms elements deserialized", deserialized)

    console.log("cms elements deserialized filtered", deserialized.filterIsInstance<HeroElement>())
}


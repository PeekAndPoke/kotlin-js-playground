import kotlinx.html.*
import kotlinx.html.js.onClickFunction
import kotlin.browser.document
import kotlin.browser.window

interface IComponent {
    fun view(): MithrilElement
}

abstract class Component : IComponent {
}

val Component.asMithril
    get() = mapOf(
        "view" to { vnode: dynamic ->
            view().render()
        }
    ).js

class ContainerComponent : Component() {

    override fun view(): MithrilElement {
        return mithril.div {
            h1 { +"Container" }

            repeat(1000) {
                helloWorld()
            }
        }
    }
}

fun HTMLTag.helloWorld() = (this.consumer as MithrilTagConsumer).onComponent {
    HelloWorldComponent()
}


class HelloWorldComponent : Component() {

    private var counter = 0

    override fun view(): MithrilElement {

        val tag = mithril.div {
            h1 {
                style = "color: red;"
                +"Hello World Component !"
            }

            div { +"Counter: $counter" }

            button {
                onClickFunction = { counter++ }
                +"+"
            }
            button {
                onClickFunction = { counter-- }
                +"-"
            }
        }

//        console.log(tag)
//        console.log(tag.render())

        return tag
    }
}


fun main() {

    var counter = 0

    document.addEventListener("DOMContentLoaded", { event ->

//        window.setInterval({
//
//            counter++
//
//            val tag = mithril.div {
//                h1 {
//                    style = "color: red;"
//                    +"Hey $counter"
//                }
//
//                a(href = "http://www.google.de") {
//                    +"google"
//                }
//
//                +"Hello World"
//            }
//
//            console.log("TAG", tag)
//
//            val rendered = tag.render()
//
//            console.log("RENDERED", rendered)
//
//            m.render(document.body!!, rendered)
//
//        }, 2000)

        val comp = ContainerComponent()

//        console.log("COMPONENT", comp.comp)
        console.log("COMPONENT", comp.view().render())

        m.mount(document.body!!, comp.asMithril)

    })
}


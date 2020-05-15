import kotlinx.html.*
import kotlinx.html.js.onClickFunction
import kotlin.browser.document


abstract class Component<PROPS, STATE>(initialProps: PROPS, initialState: STATE) {

    private var _props: PROPS = initialProps
    private var _state: STATE = initialState

    val props: PROPS get() = _props
    val state: STATE get() = _state

    abstract fun view(): MithrilElement

    fun nextProps(props: PROPS) {
        _props = props
    }

    fun modState(mod: (STATE) -> STATE) {
        _state = mod(_state)
    }
}

abstract class StaticComponent
    : Component<Nothing?, Nothing?>(null, null)

class RootComponent: StaticComponent() {
    override fun view(): MithrilElement {
        return mithril.div {
            container()
        }
    }
}

val StaticComponent.asMithrilRoot
    get() = mapOf<String, Any>(
        "key" to "root",
        "view" to { view().render() }
    ).js


fun HTMLTag.container() = (this.consumer as MithrilTagConsumer).onComponent(
    null
) { ContainerComponent() }

class ContainerComponent : Component<Nothing?, ContainerComponent.State>(null, State()) {

    data class State(
        val factor: Int = 1
    )

    override fun view(): MithrilElement {
        return mithril.div {
            h1 { +"Container" }

            button {
                onClickFunction = { modState { it.copy(factor = it.factor + 1) } }
                +"+"
            }

            pre {
                +state.toString()
            }

            repeat(3) {
                helloWorld((it + 1) * state.factor)
            }
        }
    }
}

fun <P, S> HTMLTag.comp(params: P, component: (P) -> Component<P, S>) =
    (this.consumer as MithrilTagConsumer).onComponent(params, component)

fun HTMLTag.helloWorld(factor: Int) = comp(HelloWorldComponent.Props(factor = factor)) {
    HelloWorldComponent(it)
}

class HelloWorldComponent(props: Props) : Component<HelloWorldComponent.Props, HelloWorldComponent.State>(
    props, State(counter = 0)
) {

    data class Props(
        val factor: Int
    )

    data class State(
        val counter: Int
    )

    override fun view(): MithrilElement {

        val tag = mithril.div {
            h1 {
                style = "color: red;"
                +"Hello World Component !"
            }

            div { +"Factor: ${props.factor}" }
            div { +"Counter: ${state.counter}" }
            div { +"Result: ${state.counter * props.factor}" }

            button {
                onClickFunction = { modState { it.copy(counter = it.counter + 1) } }
                +"+"
            }
            button {
                onClickFunction = { modState { it.copy(counter = it.counter - 1) } }
                +"-"
            }

//            repeat(100)  {
//                span { +"-" }
//            }
        }

//        console.log(tag)
//        console.log(tag.render())

        return tag
    }
}


fun main() {

    var counter = 0

    document.addEventListener("DOMContentLoaded", { event ->

        val comp = RootComponent()

//        console.log("COMPONENT", comp.comp)
        console.log("COMPONENT", comp.view().render())

        m.mount(document.body!!, comp.asMithrilRoot)

    })
}


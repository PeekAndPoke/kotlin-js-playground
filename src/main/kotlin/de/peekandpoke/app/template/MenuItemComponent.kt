package de.peekandpoke.app.template

import de.peekandpoke.kraft.components.*
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.FlowContent
import kotlinx.html.Tag
import kotlinx.html.div
import kotlinx.html.style
import org.w3c.dom.HTMLElement

@Suppress("FunctionName")
fun Tag.MenuItem(state: String, title: RenderFn, items: List<RenderFn>) =
    comp(MenuItemComponent.Props(state, title, items)) { MenuItemComponent(it) }

class MenuItemComponent(ctx: Ctx<Props>) : Component<MenuItemComponent.Props, MenuItemComponent.State>(
    ctx,
    State()
) {
    data class Props(
        val state: String,
        val title: FlowContent.() -> Unit,
        val items: List<FlowContent.() -> Unit>
    )

    data class State(
        val active: Boolean = false
    )

    override fun VDom.render() {

        val scrollHeight = (dom?.lastChild as? HTMLElement)?.scrollHeight

        div {
            ui.title.header.item H4 {
                icon.dropdown()
                props.title(this)
                onClick { modState { it.copy(active = !it.active) } }
            }

            ui.active.animated.content {

                console.log("DOM", dom)

                if (state.active) {
                    if (scrollHeight != null) {
                        style = "max-height: ${scrollHeight}px; transition: max-height 0.5s ease-out; overflow: hidden;"
                    }
                } else {
                    style = "max-height: 0; transition: max-height 0.5s ease-out; overflow: hidden;"
                }


                props.items.forEach {
                    it()
                }
            }
        }

    }
}

package de.peekandpoke.app.ui.components

import de.peekandpoke.kraft.components.*
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.noui
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.Tabs(items: List<TabsComponent.Item>) = comp(TabsComponent.Props(items)) { TabsComponent(it) }

class TabsComponent(ctx: Ctx<Props>) : Component<TabsComponent.Props>(ctx) {

    data class Props(
        val items: List<Item>
    )

    data class Item(
        val title: String,
        val content: RenderFn
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var selectedTab by property(0)

    ////  IMPL   ///////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        ui.top.attached.tabular.menu {
            props.items.forEachIndexed { idx, it ->
                noui.item.given(idx == selectedTab) { active } A {
                    href = "#"
                    onClick {
                        it.preventDefault()
                        selectedTab = idx
                    }
                    +it.title
                }
            }
        }

        props.items.getOrNull(selectedTab)?.let { it.content(this) }
    }
}

package de.peekandpoke.app

import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.StaticComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.routing.router
import de.peekandpoke.kraft.routing.routesToViews
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.html.Tag
import kotlinx.html.a

fun Tag.container() = comp { ContainerComponent(it) }

class ContainerComponent(ctx: Ctx<Nothing?>) : StaticComponent(ctx) {

    private val routeMaps = routesToViews {
        add(Nav.home) { home() }
        add(Nav.counters) { counters() }
        add(Nav.remote) { remote() }
    }.mapping

    override fun VDom.render() {

        ui.container {

            ui.horizontal.list {
                ui.item {
                    a(href = Nav.home()) { +"Home" }
                }
                ui.item {
                    a(href = Nav.counters()) { +"Counters" }
                }
                ui.item {
                    a(href = Nav.remote()) { +"Remote" }
                }
            }

            router(router, routeMaps)
        }
    }
}

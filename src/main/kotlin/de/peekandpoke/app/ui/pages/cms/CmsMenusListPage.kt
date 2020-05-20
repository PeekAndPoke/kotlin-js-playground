package de.peekandpoke.app.ui.pages.cms

import de.peekandpoke.app.Api
import de.peekandpoke.app.Nav
import de.peekandpoke.app.domain.cms.CmsMenuModel
import de.peekandpoke.app.ui.Theme
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.styling.css
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.*

@Suppress("FunctionName")
fun Tag.CmsMenusList() = comp { CmsMenusListPage(it) }

class CmsMenusListPage(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var items by property<List<CmsMenuModel>>(emptyList())
    private var search by property("") { reload() }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        reload()
    }

    private fun reload() {
        GlobalScope.launch {
            Api.cms.searchMenus(search).collect { items = it.data!! }
        }
    }

    override fun VDom.render() {

        ui.basic.inverted.padded.blue.segment.with("page-header") {

            ui.three.column.grid {
                ui.column {
                    ui.header H1 {
                        css(Theme.Pages.whiteText)
                        +"Cms Menus"
                    }
                }

                ui.column {
                    ui.form {
                        TextField(::search) {
                            appearance = { blue }
                            placeholder = "Search"
                        }
                    }
                }
            }
        }

        ui.basic.segment {
            ui.celled.table Table {
                thead {
                    tr {
                        th { +"Name" }
                        th { +"Updated at" }
                        th { +"Last edit by" }
                        th { +"Actions" }
                    }
                }

                tbody {
                    items.forEach {
                        tr {
                            td {
                                +it.name
                            }
                            td {
                                // TODO
                            }
                            td {
                                // TODO
                            }
                            td {

                                ui.basic.primary.icon.button A {
                                    href = Nav.cmsMenuEditor(it.id)
                                    icon.edit()
                                }

                                // TODO: we need the can delete flag in the CmsMenuModel
//                                if (cms.canDelete(it)) {
//                                    ui.red.basic.icon.button A {
//                                        href = routes.menus.delete(it).url
//                                        icon.trash()
//                                    }
//                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

package de.peekandpoke.app.ui.pages.cms

import de.peekandpoke.app.Api
import de.peekandpoke.app.Nav
import de.peekandpoke.app.domain.cms.CmsPageModel
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
fun Tag.CmsPagesList() = comp { CmsPagesListPage(it) }

class CmsPagesListPage(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var items by property<List<CmsPageModel>>(emptyList())
    private var search by property("") { reload() }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        reload()
    }

    private fun reload() {
        GlobalScope.launch {
            Api.cms.searchPages(search).collect { items = it.data!! }
        }
    }

    override fun VDom.render() {

        ui.basic.inverted.padded.blue.segment {
            css(Theme.Pages.headerPadding)
            ui.three.column.grid {
                ui.column {
                    ui.header H2 {
                        css(Theme.Pages.whiteText)
                        +"Cms Pages"
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
                        th { +"Uri" }
                        th { +"Tags" }
                        th { +"Meta" }
                        th { +"Alternate" }
                        th { +"Last update" }
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
                                +it.uri
                            }
                            td {
                                it.tags.split(" ").filter(String::isNotBlank).forEach {
                                    ui.label { +it }
                                }
                            }
                            td {
                                ui.list {
                                    ui.item { +"Robots: ${it.meta.robots}" }
                                    ui.item { +"Description: ${it.meta.description.take(30)}" }
                                }
                            }
                            td {
                                ui.list {
                                    it.meta.alternateLanguages.forEach {
                                        ui.item { +"${it.language} -> ${it.url.url}" }
                                    }
                                }
                            }
                            td {
//                                ui.list {
//                                    ui.item { +(it._meta?.ts?.updatedAt?.let { formatDateTime(it) } ?: "") }
//                                    ui.item { +"by ${(it._meta?.user?.userId ?: "n/a")}" }
//                                }
                            }
                            td {
                                ui.basic.primary.icon.button A {
                                    href = Nav.cmsPageEditor(it.id)
                                    icon.edit()
                                }

                                // TODO: we need to send the "Can-Delete" info with CmsPageModel
//                                if (cms.canDelete(it)) {
//                                    ui.red.inverted.icon.button A {
//                                        href = routes.pages.delete(it).url
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

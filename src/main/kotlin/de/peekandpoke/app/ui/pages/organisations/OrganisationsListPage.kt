package de.peekandpoke.app.ui.pages.organisations

import de.peekandpoke.app.Api
import de.peekandpoke.app.Nav
import de.peekandpoke.app.domain.organisations.OrganisationModel
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
fun Tag.OrganisationsList() = comp { OrganisationsListPage(it) }

class OrganisationsListPage(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var organisations by property<List<OrganisationModel>>(emptyList())
    private var search by property("") { reload() }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        reload()
    }

    private fun reload() {
        GlobalScope.launch {
            Api.organisations.search(search).collect { organisations = it.data!! }
        }
    }

    override fun VDom.render() {

        ui.basic.inverted.padded.blue.segment {
            css(Theme.Pages.headerPadding)
            ui.three.column.grid {
                ui.column {
                    ui.header H2 {
                        css(Theme.Pages.whiteText)
                        +"Organisations"
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
                        th { +"Actions" }
                    }
                }

                tbody {
                    organisations.forEach { org ->
                        tr {
                            td { +org.name }
                            td {
                                ui.basic.primary.icon.button A {
                                    href = Nav.organisationEditor(org.id)
                                    icon.edit()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

package de.peekandpoke.app.ui.pages.adminusers

import de.peekandpoke.app.Api
import de.peekandpoke.app.AppState
import de.peekandpoke.app.Nav
import de.peekandpoke.app.domain.adminusers.AdminUserModel
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
fun Tag.AdminUsersList() = comp { AdminUsersListPage(it) }

class AdminUsersListPage(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private val selectedOrg by stream(AppState.selectedOrg) { reload() }

    private var items by property<List<AdminUserModel>>(emptyList())
    private var search by property("") { reload() }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun reload() {
        GlobalScope.launch {
            Api.adminUsers.listInOrg(selectedOrg.id, search).collect { items = it.data!! }
        }
    }

    override fun VDom.render() {

        ui.basic.inverted.padded.blue.segment {
            css(Theme.Pages.headerPadding)
            ui.three.column.grid {
                ui.column {
                    ui.header H2 {
                        css(Theme.Pages.whiteText)
                        +"Admin Users"
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
                        th { +"Email" }
                        th { +"Status" }
                        th { +"Roles" }
                        th { +"Display Name" }
                        th { +"Avatar" }
                        th { +"Actions" }
                    }
                }

                tbody {
                    items.forEach { user ->
                        tr {
                            td { +user.name }
                            td { +user.email }
                            td { +user.status.toString() }
                            td { user.roles.forEach { ui.label { +it } } }
                            td { +user.profile.displayName }
                            td {
                                user.profile.avatar?.let {
                                    img {
                                        src = it
                                        style = "max-height: 30px;"
                                    }
                                }
                            }
                            td {
                                ui.basic.primary.icon.button A {
                                    href = Nav.adminUserEditor(user.id)
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

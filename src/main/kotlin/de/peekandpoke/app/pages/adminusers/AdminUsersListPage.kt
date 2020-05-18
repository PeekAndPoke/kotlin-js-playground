package de.peekandpoke.app.pages.adminusers

import de.peekandpoke.app.Api
import de.peekandpoke.app.domain.adminusers.AdminUserModel
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.AdminUsersList() = comp { AdminUsersListPage(it) }

class AdminUsersListPage(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private var users by property<List<AdminUserModel>>(emptyList())

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        reload()
    }

    private fun reload() {
        GlobalScope.launch {
            Api.adminUsers.list("").collect {
                users = it.data!!
                console.log(it)
            }
        }
    }

    override fun VDom.render() {

        ui.list {
            users.forEach {
                ui.item {
                    +it.name
                }
            }
        }
    }
}

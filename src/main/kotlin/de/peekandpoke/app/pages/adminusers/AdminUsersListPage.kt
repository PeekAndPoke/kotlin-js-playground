package de.peekandpoke.app.pages.adminusers

import de.peekandpoke.app.Api
import de.peekandpoke.app.domain.adminusers.AdminUserModel
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.ProplessComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.AdminUsersList() = comp { AdminUsersListPage(it) }

class AdminUsersListPage(ctx: NoProps) : ProplessComponent<AdminUsersListPage.State>(ctx) {

    inner class State {
        var users by property<List<AdminUserModel>>(emptyList())
    }

    override val state = State()

    init {
        reload()
    }

    private fun reload() {
        GlobalScope.launch {
            Api.adminUsers.list("").collect {
                state.users = it.data!!
                console.log(it)
            }
        }
    }

    override fun VDom.render() {

        ui.list {
            state.users.forEach {
                ui.item {
                    +it.name
                }
            }
        }
    }
}

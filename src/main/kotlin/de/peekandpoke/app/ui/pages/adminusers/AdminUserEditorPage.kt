package de.peekandpoke.app.ui.pages.adminusers

import de.peekandpoke.app.*
import de.peekandpoke.app.domain.adminusers.AdminUserModel
import de.peekandpoke.app.domain.adminusers.AdminUserStatus
import de.peekandpoke.app.domain.domainCodec
import de.peekandpoke.app.ui.Theme
import de.peekandpoke.app.ui.components.forms.CheckboxField
import de.peekandpoke.app.ui.components.forms.FormComponent
import de.peekandpoke.app.ui.components.forms.SelectField
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.components.forms.validation.NotBlank
import de.peekandpoke.app.ui.components.forms.validation.NotEmpty
import de.peekandpoke.app.ui.components.forms.validation.ValidEmail
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.styling.css
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.FlowContent
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.AdminUsersEditor(id: String) = comp(AdminUserEditorPage.Props(id)) { AdminUserEditorPage(it) }

class AdminUserEditorPage(ctx: Ctx<Props>) : FormComponent<AdminUserEditorPage.Props>(ctx) {

    data class Props(
        val id: String
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /** The user that is currently logged in */
    private val loggedInUser by stream(AppState.user)

    /** The original version */
    private var original by property<AdminUserModel?>(null) { draft = it }

    /** The draft version that we are editing */
    private var draft by property<AdminUserModel?>(null) {
        it?.let {
            console.log("AdminUser draft", domainCodec.stringify(AdminUserModel.serializer(), it))
        }
    }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        reload()
    }

    override fun VDom.render() {
        ui.basic.inverted.blue.segment {
            css(Theme.Pages.headerPadding)

            ui.header H2 {
                icon.small.edit()
                +"Edit Admin User '${original?.name}'"
            }
        }

        ui.basic.segment {
            basicInfo()
            roles()

            // only super users can change the SuperUser role
            if (loggedInUser.isSuperUser) {
                superUserRole()
            }

            ui.segment {
                ui.button Button {
                    +"Save"; disabled = !isValid; onClick { save() }
                }
            }
        }
    }

    private fun reload() {
        GlobalScope.launch {
            Api.adminUsers
                .getInOrg(AppState.selectedOrg().id, props.id)
                .collect { original = it.data!! }
        }
    }

    private fun save() {
        draft?.let {
            GlobalScope.launch {
                Api.adminUsers
                    .save(it.org.id, it.id, it)
                    .collect { original = it.data!! }
            }
        }
    }

    private fun FlowContent.basicInfo() {
        draft?.apply {
            ui.segment {
                ui.form {
                    ui.three.fields {

                        TextField({ email }, { draft = copy(email = it) }) {
                            label = "Email"
                            accepts(ValidEmail)
                        }

                        TextField({ name }, { draft = copy(name = it) }) {
                            label = "Name"
                            accepts(NotBlank)
                        }

                        SelectField({ status }, { draft = copy(status = it) }) {
                            label = "Status"
                            accepts(NotEmpty)
                            option(AdminUserStatus.ACTIVE, "active") { +"Active" }
                            option(AdminUserStatus.INACTIVE, "inactive") { +"Inactive" }
                        }
                    }
                }
            }
        }
    }

    private fun FlowContent.roles() {
        draft?.apply {
            ui.segment {
                ui.header H3 { +"Roles" }

                ui.form {
                    ui.fields {
                        Roles.forEach { role ->
                            CheckboxField(
                                { roles.contains(role) },
                                { draft = copy(roles = if (it) roles.plus(role) else roles.minus(role)) }
                            ) {
                                label = role
                            }
                        }
                    }
                }
            }
        }
    }

    private fun FlowContent.superUserRole() {
        draft?.apply {
            ui.segment {
                ui.red.header H3 { +"Super User Rights" }

                ui.form {
                    if (isSuperUser) {
                        ui.green.button {
                            +"Revoke Super User Rights"
                            onClick { draft = copy(roles = roles.minus(SuperUserRole)) }
                        }
                    } else {
                        ui.red.button {
                            +"! Give Super User Rights !"
                            onClick { draft = copy(roles = roles.plus(SuperUserRole)) }
                        }
                    }
                }
            }
        }
    }
}

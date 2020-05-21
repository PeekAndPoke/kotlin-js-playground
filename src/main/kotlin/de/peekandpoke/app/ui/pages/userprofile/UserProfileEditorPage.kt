package de.peekandpoke.app.ui.pages.userprofile

import de.peekandpoke.app.Api
import de.peekandpoke.app.AppState
import de.peekandpoke.app.domain.adminusers.AdminUserProfile
import de.peekandpoke.app.domain.domainCodec
import de.peekandpoke.app.ui.components.Tabs
import de.peekandpoke.app.ui.components.TabsComponent
import de.peekandpoke.app.ui.components.forms.FormComponent
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.components.forms.validation.NotBlank
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.FlowContent
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.UserProfileEditor() = comp { UserProfileEditorPage(it) }

class UserProfileEditorPage(ctx: NoProps) : FormComponent<Nothing?>(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /** The draft version that we are editing */
    private var draft by property<AdminUserProfile?>(null) {
        it?.let {
            console.log("Profile draft", domainCodec.stringify(AdminUserProfile.serializer(), it))
        }
    }

    /** The original version */
    private val user by stream(AppState.user) { draft = it?.profile }

    /** The auth claims of the currently logged in user */
    private val claims by stream(AppState.authClaims)

    /** The permissions of the currently logged in user */
    private val permissions by stream(AppState.permissions)

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        ui.basic.inverted.blue.segment.with("page-header") {

            ui.header H2 {
                icon.small.edit()
                +"Edit your profile"
            }
        }

        ui.basic.segment {
            Tabs(
                listOf(
                    TabsComponent.Item(title = "Profile") { profile() },
                    TabsComponent.Item(title = "Account") { account() }
                )
            )
        }
    }

    private fun save() {
        user?.let { user ->
            draft?.let { draft ->
                GlobalScope.launch {
                    Api.adminUsers
                        .saveProfile(user.org.id, user.id, draft)
                        .collect { AppState.UserActions.set(it.data!!) }
                }
            }
        }
    }

    private fun FlowContent.profile() {

        draft?.apply {
            ui.attached.segment {
                ui.form {
                    ui.three.fields {
                        TextField(displayName, { draft = copy(displayName = it) }) {
                            label = "Display Name"
                            accepts(NotBlank)
                        }
                    }
                }
            }
            ui.bottom.attached.segment {
                ui.button Button {
                    +"Save"; disabled = !isValid; onClick { save() }
                }
            }
        }
    }

    private fun FlowContent.account() {

        user?.let { user ->

            ui.attached.segment {

                ui.segment {
                    ui.header H4 { +"Account" }
                    ui.list {
                        ui.item { +"Id: ${user.id}" }
                        ui.item { +"Name: ${user.name}" }
                        ui.item { +"Email: ${user.email}" }
                    }
                }

                ui.segment {
                    ui.header H4 { +"Permissions" }
                    ui.list {
                        ui.item { +"Groups: ${permissions.groups.joinToString(", ")}" }
                        ui.item { +"Roles: ${permissions.roles.joinToString(", ")}" }
                        ui.item { +"Permissions: ${permissions.permissions.joinToString(", ")}" }
                    }
                }

                claims?.let { claims ->
                    ui.segment {
                        ui.header H4 { +"Claims" }
                        ui.list {
                            claims.forEach { (k, v) ->
                                ui.item { +"$k: $v" }
                            }
                        }
                    }
                }
            }
        }
    }
}

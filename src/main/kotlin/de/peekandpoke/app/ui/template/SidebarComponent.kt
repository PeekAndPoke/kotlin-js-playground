package de.peekandpoke.app.ui.template

import de.peekandpoke.app.*
import de.peekandpoke.app.ui.Theme
import de.peekandpoke.kraft.auth.hasAnyRole
import de.peekandpoke.kraft.components.*
import de.peekandpoke.kraft.styling.css
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.custom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.css.*
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.translateX
import kotlinx.html.*
import org.w3c.dom.HTMLSelectElement

@Suppress("FunctionName")
fun Tag.Sidebar() = comp { SidebarComponent(it) }

class SidebarComponent(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    private val user by stream(AppState.user)
    private val permissions by stream(AppState.permissions)
    private val activeRoute by stream(mainRouter.current)
    private val selectedOrg by stream(AppState.selectedOrg)
    private val selectableOrgs by stream(AppState.selectableOrgs)

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        custom("sidebar") {

            ui.header.item H3 {
                onClick {
                    mainRouter.navTo(Nav.home())
                }
                img {
                    src = "/admin-assets/logo-small.png"
                    style = "display: inline; padding-right: 10px;"
                }
                +"The Base"
            }

            if (permissions.isSuperUser && selectableOrgs.size > 1) {
                ui.form {
                    ui.grey.field {
                        select {
                            css {
                                backgroundColor = Theme.Colors.lightDark
                                color = Color.whiteSmoke
                            }
                            onChange { event ->
                                selectableOrgs
                                    .filter { it.id == (event.target as HTMLSelectElement).value }
                                    .forEach { AppState.SelectedOrgActions.set(it) }
                            }
                            selectableOrgs.forEach {
                                option {
                                    value = it.id
                                    selected = it.id == selectedOrg.id
                                    +it.name
                                }
                            }
                        }
                    }
                }
            }

            user?.let { user ->
                ui.center.aligned.segment {
                    css {
                        paddingBottom = 47.px
                        backgroundColor = Theme.Colors.dark
                        color = Color.white
                    }

                    ui.header H4 {
                        css { color = Color.white }
                        +user.profile.displayName
                    }
                    span { +user.email }

                    user.profile.avatar?.let { avatar ->
                        div {
                            css {
                                position = Position.absolute
                                top = 82.px
                                borderRadius = 50.pct
                                padding = "8px"
                                transform = Transforms().apply { translateX((-50).pct) }
                                left = 50.pct
                                backgroundColor = Theme.Colors.lightDark
                            }
                            img {
                                css {
                                    borderRadius = 50.pct
                                    width = 72.px
                                    height = 72.px
                                    margin = "0"
                                }
                                src = avatar
                            }
                        }
                    }
                }
            }

            ui.accordion {
                css {
                    paddingTop = 50.px
                    backgroundColor = Theme.Colors.lightDark
                }

                administration()
                cms()
                demo()
            }
        }
    }

    private fun FlowContent.administration() {

        val items = mutableListOf<RenderFn>()

        if (permissions.hasAnyRole(SuperUserRole, AdminUsersRole)) {
            items.add {
                ui.item.given(activeRoute.route == Nav.adminUsersList) { active } A {
                    href = Nav.adminUsersList()
                    +"Admin users"
                }
            }
        }

        if (permissions.hasAnyRole(SuperUserRole)) {
            items.add {
                ui.item.given(activeRoute.route == Nav.organisationsList) { active } A {
                    href = Nav.organisationsList()
                    +"Organisations"
                }
            }
        }

        if (items.isNotEmpty()) {
            MenuItem(
                state = activeRoute.pattern,
                title = {
                    div {
                        icon.user_shield()
                        +"Administration"
                    }
                },
                items = items
            )
        }
    }

    private fun FlowContent.cms() {
        if (permissions.hasAnyRole(SuperUserRole, CmsRole)) {

            MenuItem(
                state = activeRoute.pattern,
                title = {
                    div {
                        icon.file_alternate_outline()
                        +"Cms"
                    }
                },
                items = listOf<RenderFn>(
                    {
                        ui.item.given(activeRoute.route == Nav.cmsPagesList) { active } A {
                            href = Nav.cmsPagesList()
                            +"Pages"
                        }
                    },
                    {
                        ui.item.given(activeRoute.route == Nav.cmsMenusList) { active } A {
                            href = Nav.cmsMenusList()
                            +"Menus"
                        }
                    },
                    {
                        ui.item.given(activeRoute.route == Nav.cmsSnippetsList) { active } A {
                            href = Nav.cmsSnippetsList()
                            +"Snippets"
                        }
                    }
                )
            )
        }
    }

    private fun FlowContent.demo() {

        if (permissions.isSuperUser) {

            MenuItem(
                state = activeRoute.pattern,
                title = {
                    div {
                        icon.building_outline()
                        +"Demo"
                    }
                },
                items = listOf<RenderFn>(
                    {
                        ui.item.given(activeRoute.route == Nav.counters) { active } A {
                            href = Nav.counters()
                            +"Counters"
                        }
                    }, {
                        ui.item.given(activeRoute.route == Nav.remote) { active } A {
                            href = Nav.remote()
                            +"Remote"
                        }
                    }
                )
            )
        }
    }
}

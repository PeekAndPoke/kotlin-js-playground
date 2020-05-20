package de.peekandpoke.app

import de.peekandpoke.app.ui.pages.HomePage
import de.peekandpoke.app.ui.pages.LoginPage
import de.peekandpoke.app.ui.pages.adminusers.AdminUsersEditor
import de.peekandpoke.app.ui.pages.adminusers.AdminUsersList
import de.peekandpoke.app.ui.pages.cms.*
import de.peekandpoke.app.ui.pages.demo.CountersPage
import de.peekandpoke.app.ui.pages.demo.RemotePage
import de.peekandpoke.app.ui.pages.organisations.OrganisationEditor
import de.peekandpoke.app.ui.pages.organisations.OrganisationsList
import de.peekandpoke.app.ui.pages.userprofile.UserProfileEditor
import de.peekandpoke.kraft.routing.*

object Nav {
    val login = StaticRoute("/login")
    val home = StaticRoute("")

    val userProfileEditor = StaticRoute("/profile")

    val adminUsersList = StaticRoute("/administration/adminusers")
    val adminUserEditor = ParameterizedRoute1("/administration/adminusers/{id}")

    val cmsPagesList = StaticRoute("/cms/pages")
    val cmsPageEditor = ParameterizedRoute1("/cms/pages/{id}")
    val cmsMenusList = StaticRoute("/cms/menus")
    val cmsMenuEditor = ParameterizedRoute1("/cms/menus/{id}")
    val cmsSnippetsList = StaticRoute("/cms/snippets")
    val cmsSnippetEditor = ParameterizedRoute1("/cms/snippets/{id}")

    val organisationsList = StaticRoute("/administration/organisations")
    val organisationEditor = ParameterizedRoute1("/administration/organisations/{id}")

    // Demo pages
    val counters = StaticRoute("/counters")
    val remote = StaticRoute("/remote")
}

val isLoggedIn: RouterMiddleware = routerMiddleware {
    if (!AppState.permissions().isLoggedIn) {
        mainRouter.navTo(Nav.login())
    }
}

val mainRouter = router {

    mount(Nav.login) { LoginPage() }

    using(isLoggedIn) {

        mount(Nav.home) { HomePage() }

        mount(Nav.userProfileEditor) { UserProfileEditor() }

        mount(Nav.adminUsersList) { AdminUsersList() }
        mount(Nav.adminUserEditor) { AdminUsersEditor(it["id"]) }

        mount(Nav.cmsPagesList) { CmsPagesList() }
        mount(Nav.cmsPageEditor) { CmsPageEditor(it["id"]) }
        mount(Nav.cmsMenusList) { CmsMenusList() }
        mount(Nav.cmsMenuEditor) { CmsMenuEditor(it["id"]) }
        mount(Nav.cmsSnippetsList) { CmsSnippetsList() }
        mount(Nav.cmsSnippetEditor) { CmsSnippetEditor(it["id"]) }

        mount(Nav.organisationsList) { OrganisationsList() }
        mount(Nav.organisationEditor) { OrganisationEditor(it["id"]) }

        // Demo Pages
        mount(Nav.counters) { CountersPage() }
        mount(Nav.remote) { RemotePage() }
    }
}

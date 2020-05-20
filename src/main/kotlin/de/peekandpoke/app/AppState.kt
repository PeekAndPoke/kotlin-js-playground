package de.peekandpoke.app

import de.peekandpoke.app.domain.adminusers.AdminUserModel
import de.peekandpoke.app.domain.organisations.OrganisationModel
import de.peekandpoke.app.utils.createAvatarImage
import de.peekandpoke.kraft.auth.Permissions
import de.peekandpoke.kraft.auth.decodeJwtBody
import de.peekandpoke.kraft.store.Stream
import de.peekandpoke.kraft.store.WritableStream
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

object AppState {

    object Actions {
        fun logIn(user: AdminUserModel, token: String) {

            // Set the user in the AppState
            UserActions.set(user.withGeneratedAvatar())
            // Set the auth token in the AppState
            AuthTokenActions.set(token)
            // Set the selected organisation
            SelectedOrgActions.set(user.org)

            val claims = decodeJwtBody(token)
            AuthClaimsActions.set(claims)

            // extract the permission from the token
            val namespace = "https://api.jointhebase.co/auth"
            @Suppress("UNCHECKED_CAST")
            PermissionsActions.set(
                Permissions(
                    isLoggedIn = true,
                    groups = claims["$namespace/groups"] as? List<String> ?: emptyList(),
                    roles = claims["$namespace/roles"] as? List<String> ?: emptyList(),
                    permissions = claims["$namespace/permissions"] as? List<String> ?: emptyList()
                )
            )

            // Load all selectable organisations for this user
            SelectableOrgsActions.load()
        }

        private fun AdminUserModel.withGeneratedAvatar() = when {
            profile.avatar != null -> this

            else -> copy(
                profile = profile.copy(
                    avatar = createAvatarImage(name)
                )
            )
        }
    }

    ////  Logged in user ///////////////////////////////////////////////////////////////////////////////////////////////

    private val userStream = WritableStream<AdminUserModel?>(null)
    val user = userStream.readonly

    object UserActions {
        fun set(value: AdminUserModel) = userStream.next(value)
        fun clear() = userStream.next(null)
    }

    ////  Authentication  //////////////////////////////////////////////////////////////////////////////////////////////

    private val authTokenStream = WritableStream<String?>(null)
    val authToken = authTokenStream.readonly

    object AuthTokenActions {
        fun set(value: String) = authTokenStream.next(value)
        fun clear() = authTokenStream.next(null)
    }

    private val authClaimsStream = WritableStream<Map<String, Any?>?>(null)
    val authClaims = authClaimsStream.readonly

    object AuthClaimsActions {
        fun set(value: Map<String, Any?>?) = authClaimsStream.next(value)
    }

    private val permissionsStream = WritableStream(Permissions())
    val permissions = permissionsStream.readonly

    object PermissionsActions {
        fun set(value: Permissions) = permissionsStream.next(value)
    }

    ////  Selected Org  ////////////////////////////////////////////////////////////////////////////////////////////////

    private val selectedOrgStream = WritableStream(OrganisationModel("", ""))
    val selectedOrg: Stream<OrganisationModel> =
        selectedOrgStream

    object SelectedOrgActions {
        fun set(value: OrganisationModel) = selectedOrgStream.next(value)
    }

    ////  Selectable Organisations  ////////////////////////////////////////////////////////////////////////////////////

    private val selectableOrgsStream = WritableStream<List<OrganisationModel>>(emptyList())
    val selectableOrgs: Stream<List<OrganisationModel>> =
        selectableOrgsStream

    object SelectableOrgsActions {
        fun set(value: List<OrganisationModel>) = selectableOrgsStream.next(value)

        fun load() {
            if (permissions.isSuperUser) {
                GlobalScope.launch {
                    Api.organisations.search().collect {
                        set(it.data!!)
                    }
                }
            }
        }
    }
}


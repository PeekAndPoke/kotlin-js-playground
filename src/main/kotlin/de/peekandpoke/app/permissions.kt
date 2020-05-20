package de.peekandpoke.app

import de.peekandpoke.kraft.auth.Permissions
import de.peekandpoke.kraft.auth.hasRole
import de.peekandpoke.kraft.store.Stream

const val SuperUserRole = "SUPER_USER"

const val AdminUsersRole = "ADMIN_USERS"
const val CmsRole = "CMS"

val Roles = listOf(
    AdminUsersRole,
    CmsRole
)

/** Returns 'true' when the current user has the SUPER_USER roles */
val Permissions.isSuperUser: Boolean get() = hasRole(SuperUserRole)

/** Returns 'true' when the current user has the SUPER_USER roles */
val Stream<Permissions>.isSuperUser: Boolean get() = invoke().isSuperUser

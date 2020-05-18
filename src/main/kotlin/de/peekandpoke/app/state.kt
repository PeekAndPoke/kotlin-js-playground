package de.peekandpoke.app

import de.peekandpoke.app.domain.adminusers.AdminUserModel
import de.peekandpoke.kraft.meiosis.Stream

object AppState {

    val user = Stream<AdminUserModel?>(null)

    object UserActions {
        fun set(value: AdminUserModel) = user.next(value)
        fun clear() = user.next(null)
    }

    val authToken = Stream<String?>(null)

    object AuthTokenActions {
        fun set(value: String) = authToken.next(value)
        fun clear() = authToken.next(null)
    }
}


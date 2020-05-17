package de.peekandpoke.app

import de.peekandpoke.kraft.meiosis.Stream

data class User(
    val id: String
)

val userState = Stream<User?>(null)

object UserActions {

    fun set(user: User) = userState.next(user)

    fun clear() = userState.next(null)
}

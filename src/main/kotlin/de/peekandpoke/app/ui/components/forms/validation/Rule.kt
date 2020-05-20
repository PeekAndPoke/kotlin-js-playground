package de.peekandpoke.app.ui.components.forms.validation

interface Rule<T> {
    operator fun invoke(value: T): Boolean

    val message: String
}

object NotEmpty: Rule<String> {
    override fun invoke(value: String) = value.isNotEmpty()

    override val message = "Must not be empty"
}

object NotBlank: Rule<String> {
    override fun invoke(value: String) = value.isNotBlank()

    override val message = "Must not be blank"
}

object ValidEmail: Rule<String> {
    private val emailRegex = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex()

    override fun invoke(value: String) = value.isNotBlank() && emailRegex.matches(value)

    override val message = "Must be a valid email"
}

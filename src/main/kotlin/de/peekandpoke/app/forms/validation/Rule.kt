package de.peekandpoke.app.forms.validation

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

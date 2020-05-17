package de.peekandpoke.kraft.meiosis

class Stream<T>(initialValue: T) {

    private var current = initialValue

    private val subscriptions = mutableSetOf<(T) -> Unit>()

    fun next(value: T) {
        this.current = value

        subscriptions.forEach { it(value) }
    }

    fun subscribeToStream(sub: (T) -> Unit): () -> Unit {
        this.subscriptions.add(sub)

        return {
            subscriptions.remove(sub)
        }
    }
}

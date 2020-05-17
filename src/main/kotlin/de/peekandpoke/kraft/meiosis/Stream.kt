package de.peekandpoke.kraft.meiosis

class Stream<T>(initialValue: T) {

    private var current = initialValue

    private val subscriptions = mutableSetOf<(T) -> Unit>()

    operator fun invoke(): T = current

    fun next(value: T) {
        this.current = value

        subscriptions.forEach { it(value) }
    }

    /**
     * Adds a subscription to the stream.
     *
     * On subscribing the subscription is immediately called with the current value.
     *
     * Returns an unsubscribe function. Calling this function unsubscribes from the stream.
     */
    fun subscribeToStream(sub: (T) -> Unit): () -> Unit {
        this.subscriptions.add(sub)

        sub(current)

        return {
            subscriptions.remove(sub)
        }
    }
}

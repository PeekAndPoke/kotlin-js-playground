package de.peekandpoke.kraft.store

abstract class Stream<T>(initialValue: T) {

    protected var current = initialValue

    protected val subscriptions = mutableSetOf<(T) -> Unit>()

    /**
     * Returns the current value of the stream
     */
    operator fun invoke(): T = current

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

class WritableStream<T>(initialValue: T): Stream<T>(initialValue) {

    val readonly = this as Stream<T>

    fun next(value: T) {
        this.current = value

        subscriptions.forEach { it(value) }
    }
}

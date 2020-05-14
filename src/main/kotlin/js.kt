val <T> Map<String, T>.js
    get() : dynamic {
        val obj = js("({})")

        forEach { (k, v) -> obj[k] = v }

        return obj
    }

val <T> List<T>.js
    get(): dynamic {
        val arr = js("([])")

        forEach { arr.push(it) }

        return arr
    }

package de.peekandpoke.kraft.vdom

import kotlinx.html.*

fun FlowContent.custom(name: String, block : FlowContent.() -> Unit = {}) = CustomTag(name, consumer).visit(block)

class CustomTag(name: String, consumer: TagConsumer<*>):
    HTMLTag(name, consumer, emptyMap(), null, false, false), FlowContent

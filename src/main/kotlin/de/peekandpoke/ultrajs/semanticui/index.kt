package de.peekandpoke.ultrajs.semanticui

import kotlinx.html.FlowContent

@SemanticUiDslMarker
val FlowContent.ui
    get() = SemanticTag(this, mutableListOf("ui"))

@SemanticUiDslMarker
val FlowContent.noui
    get() = SemanticTag(this, mutableListOf(""))

@SemanticIconMarker
val FlowContent.icon
    get() = SemanticIcon(this)

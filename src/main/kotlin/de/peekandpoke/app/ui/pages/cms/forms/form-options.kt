package de.peekandpoke.app.ui.pages.cms.forms

import de.peekandpoke.app.ui.components.forms.SelectField
import de.peekandpoke.ultrajs.semanticui.SemanticColor

fun SelectField.PropsBuilder<SemanticColor>.colorOptions() {
    option(SemanticColor.default) { +"default" }
    option(SemanticColor.red) { +"light red" }
    option(SemanticColor.green) { +"light green" }
    option(SemanticColor.teal) { +"light blue" }
    option(SemanticColor.violet) { +"dark violet" }
    option(SemanticColor.olive) { +"dark green" }
    option(SemanticColor.blue) { +"dark blue" }
    option(SemanticColor.white) { +"white" }
    option(SemanticColor.black) { +"black" }
}

fun SelectField.PropsBuilder<String>.patternOptions() {
    option("") { +"No Pattern" }
    option("hero001") { +"Hero #1" }
    option("hero002") { +"Hero #2" }
    option("hero003") { +"Hero #3" }
    option("divider001") { +"Divider #1" }
    option("divider002") { +"Divider #2" }
    option("divider003") { +"Divider #3" }
    option("divider004") { +"Divider #4" }
    option("divider005") { +"Divider #5" }
    option("divider006") { +"Divider #6" }
}

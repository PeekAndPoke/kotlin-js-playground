package de.peekandpoke.app.ui.pages.organisations

import de.peekandpoke.app.Api
import de.peekandpoke.app.domain.domainCodec
import de.peekandpoke.app.domain.organisations.OrganisationModel
import de.peekandpoke.app.ui.Theme
import de.peekandpoke.app.ui.components.forms.FormComponent
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.components.forms.validation.NotBlank
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.styling.css
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultrajs.semanticui.icon
import de.peekandpoke.ultrajs.semanticui.ui
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.html.FlowContent
import kotlinx.html.Tag
import kotlinx.html.classes

@Suppress("FunctionName")
fun Tag.OrganisationEditor(id: String) = comp(OrganisationEditorPage.Props(id)) { OrganisationEditorPage(it) }

class OrganisationEditorPage(ctx: Ctx<Props>) : FormComponent<OrganisationEditorPage.Props>(ctx) {

    data class Props(
        val id: String
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /** The original version */
    private var original by property<OrganisationModel?>(null) { draft = it }

    /** The draft version that we are editing */
    private var draft by property<OrganisationModel?>(null) {
        it?.let {
            console.log("Organisation draft", domainCodec.stringify(OrganisationModel.serializer(), it))
        }
    }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        reload()
    }

    override fun VDom.render() {
        ui.basic.inverted.blue.segment.with("page-header") {

            ui.header H1 {
                icon.small.edit()
                +"Edit Organisation '${original?.name}'"
            }
        }

        ui.basic.segment {
            basicInfo()

            ui.segment {
                ui.button Button {
                    +"Save"; disabled = !isValid; onClick { save() }
                }
            }
        }
    }

    private fun reload() {
        GlobalScope.launch {
            Api.organisations
                .get(props.id)
                .collect { original = it.data!! }
        }
    }

    private fun save() {
        draft?.let {
            GlobalScope.launch {
                Api.organisations
                    .save(it.id, it)
                    .collect { original = it.data!! }
            }
        }
    }

    private fun FlowContent.basicInfo() {
        draft?.apply {
            ui.segment {
                ui.form {
                    ui.three.fields {

                        TextField({ name }, { draft = copy(name = it) }) {
                            label = "Name"
                            accepts(NotBlank)
                        }
                    }
                }
            }
        }
    }
}

package de.peekandpoke.app.ui.pages.cms

import de.peekandpoke.app.Api
import de.peekandpoke.app.domain.cms.CmsSnippetModel
import de.peekandpoke.app.domain.domainCodec
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

@Suppress("FunctionName")
fun Tag.CmsSnippetEditor(id: String) = comp(CmsSnippetEditorPage.Props(id)) { CmsSnippetEditorPage(it) }

class CmsSnippetEditorPage(ctx: Ctx<Props>) : FormComponent<CmsSnippetEditorPage.Props>(ctx) {

    data class Props(
        val id: String
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /** The original version */
    private var original by property<CmsSnippetModel?>(null) { draft = it }

    /** The draft version that we are editing */
    private var draft by property<CmsSnippetModel?>(null) {
        it?.let {
            console.log("CmsSnippet draft", domainCodec.stringify(CmsSnippetModel.serializer(), it))
        }
    }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        reload()
    }

    override fun VDom.render() {
        ui.basic.inverted.blue.segment {
            css(Theme.Pages.headerPadding)

            ui.grid {
                ui.ten.wide.column {
                    ui.header H1 {
                        css(Theme.Pages.whiteText)
                        icon.small.edit()
                        +"Edit Cms Snippet '${original?.name}'"

                    }
                }
                ui.right.aligned.six.wide.column {
                    ui.inverted.button Button {
                        +"Save"
                        disabled = !isValid || original == draft
                        onClick { save() }
                    }
                }
            }
        }

        ui.basic.segment {
            basicInfo()
        }
    }

    private fun reload() {
        GlobalScope.launch {
            Api.cms
                .getSnippet(props.id)
                .collect { original = it.data!! }
        }
    }

    private fun save() {
        draft?.let {
            GlobalScope.launch {
                Api.cms
                    .saveSnippet(it.id, it)
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

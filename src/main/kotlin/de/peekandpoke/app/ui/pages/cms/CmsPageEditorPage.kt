package de.peekandpoke.app.ui.pages.cms

import de.peekandpoke.app.Api
import de.peekandpoke.app.domain.cms.CmsPageModel
import de.peekandpoke.app.domain.cms.layouts.LandingPageLayout
import de.peekandpoke.app.ui.Theme
import de.peekandpoke.app.ui.components.forms.FormComponent
import de.peekandpoke.app.ui.components.forms.TextField
import de.peekandpoke.app.ui.components.forms.validation.NotBlank
import de.peekandpoke.app.ui.pages.cms.forms.CmsPageMetaForm
import de.peekandpoke.app.ui.pages.cms.layouts.LandingPageLayoutEditor
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
import kotlinx.html.p

@Suppress("FunctionName")
fun Tag.CmsPageEditor(id: String) = comp(CmsPageEditorPage.Props(id)) { CmsPageEditorPage(it) }

class CmsPageEditorPage(ctx: Ctx<Props>) : FormComponent<CmsPageEditorPage.Props>(ctx) {

    data class Props(
        val id: String
    )

    ////  STATE  ///////////////////////////////////////////////////////////////////////////////////////////////////////

    /** The original version */
    private var original by property<CmsPageModel?>(null) { draft = it }

    /** The draft version that we are editing */
    private var draft by property<CmsPageModel?>(null) {
//        it?.let {
//            console.log("CmsPage draft", domainCodec.stringify(CmsPageModel.serializer(), it))
//        }
    }

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    init {
        reload()
    }

    override fun VDom.render() {

        header()

        ui.basic.segment {
            basicInfo()
        }

        ui.basic.segment {
            meta()
        }

        draft?.apply {
            when (layout) {
                is LandingPageLayout ->
                    LandingPageLayoutEditor(layout) { draft = copy(layout = it) }

                else ->
                    ui.error.message {
                        p { +"There is no editor available for '${layout::class.simpleName}'" }
                    }
            }
        }
    }

    private fun reload() {
        GlobalScope.launch {
            Api.cms.getPage(props.id).collect { original = it.data!! }
        }
    }

    private fun save() {
        draft?.let {
            GlobalScope.launch {
                Api.cms.savePage(it.id, it).collect { original = it.data!! }
            }
        }
    }

    private fun FlowContent.header() {
        ui.basic.inverted.blue.segment.with("page-header") {

            ui.grid {
                ui.ten.wide.column {
                    ui.header H1 {
                        css(Theme.Pages.whiteText)
                        icon.small.edit()
                        +"Edit Cms Page '${original?.name}'"

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
    }

    private fun FlowContent.basicInfo() {
        draft?.apply {
            ui.segment {
                ui.form {
                    ui.three.fields {
                        TextField(name, { draft = copy(name = it) }) {
                            label = "Name"
                            accepts(NotBlank)
                        }
                        TextField(uri, { draft = copy(uri = it) }) {
                            label = "Uri"
                            // TODO: Validate that the uri does not yet exist
                            accepts(NotBlank)
                        }
                        TextField(tags, { draft = copy(tags = it) }) {
                            label = "Tags"
                            accepts(NotBlank)
                        }
                    }
                }
            }
        }
    }

    private fun FlowContent.meta() {

        draft?.apply {
            ui.segment {
                CmsPageMetaForm(meta) { draft = copy(meta = it) }
            }
        }
    }
}
